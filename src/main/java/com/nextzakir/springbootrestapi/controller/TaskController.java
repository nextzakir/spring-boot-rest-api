package com.nextzakir.springbootrestapi.controller;

import com.nextzakir.springbootrestapi.dto.TaskDTO;
import com.nextzakir.springbootrestapi.entity.Task;
import com.nextzakir.springbootrestapi.exception.*;
import com.nextzakir.springbootrestapi.helper.EntityState;
import com.nextzakir.springbootrestapi.helper.Helper;
import com.nextzakir.springbootrestapi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    private static final int MAX_PAGE_SIZE = 20;

    @PostMapping("/save/")
    public ResponseEntity<Task> saveTask(@RequestBody TaskDTO taskDTO) {

        if (taskDTO.getTaskTitle() != null && ! taskDTO.getTaskTitle().isBlank()) {
            if (taskDTO.getTaskTitle().length() <= 50) {
                if (taskDTO.getTaskDescription() != null && ! taskDTO.getTaskDescription().isBlank()) {
                    if (taskDTO.getTaskDescription().length() <= 150) {
                        if (! taskService.taskTitleExists(taskDTO.getTaskTitle())) {
                            Task theTask = new Task();
                            theTask.setTaskTitle(taskDTO.getTaskTitle());
                            theTask.setTaskSlug(Helper.toSlug(taskDTO.getTaskTitle()));
                            theTask.setTaskDescription(taskDTO.getTaskDescription());
                            theTask.setTaskState(EntityState.Incomplete.toString());
                            theTask.setCreatedAt(Helper.getCurrentTimestamp());
                            theTask.setUpdatedAt(Helper.getCurrentTimestamp());

                            try {
                                Task task = taskService.saveAndReturnTask(theTask);
                                return new ResponseEntity<>(task, HttpStatus.CREATED);
                            } catch (Exception e) {
                                System.err.println(e.getMessage());
                                throw new InternalServerErrorException("Something went wrong on the server!");
                            }
                        } else {
                            throw new ConflictException("A task with this title already exists!");
                        }
                    } else {
                        throw new UnprocessableEntityException("Task description must not be more than 150 characters!");
                    }
                } else {
                    throw new UnprocessableEntityException("Task description is required!");
                }
            } else {
                throw new UnprocessableEntityException("Task title must not be more than 50 characters!");
            }
        } else {
            throw new UnprocessableEntityException("Task title is required!");
        }

    }

    @PutMapping("/update/by/task-id/{taskRdbmsId}")
    public ResponseEntity<Task> updateTaskByTaskRdbmsId(@PathVariable("taskRdbmsId") Long taskRdbmsId, @RequestBody TaskDTO taskDTO) {

        if (taskRdbmsId != null) {
            if (taskDTO.getTaskTitle() != null || taskDTO.getTaskDescription() != null || taskDTO.getTaskState() != null) {
                Optional<Task> optionalTask = taskService.findTaskByTaskRdbmsId(taskRdbmsId);

                if (optionalTask.isPresent()) {
                    Task theTask = optionalTask.get();

                    if (taskDTO.getTaskTitle() != null && ! taskDTO.getTaskTitle().isBlank()) {
                        if (taskDTO.getTaskTitle().length() <= 50) {
                            theTask.setTaskTitle(taskDTO.getTaskTitle());
                            theTask.setTaskSlug(Helper.toSlug(taskDTO.getTaskTitle()));
                        } else {
                            throw new UnprocessableEntityException("Task title must not be more than 50 characters!");
                        }
                    }

                    if (taskDTO.getTaskDescription() != null && ! taskDTO.getTaskDescription().isBlank()) {
                        if (taskDTO.getTaskDescription().length() <= 150) {
                            theTask.setTaskDescription(taskDTO.getTaskDescription());
                        } else {
                            throw new UnprocessableEntityException("Task description must not be more than 150 characters!");
                        }
                    }

                    if (taskDTO.getTaskState() != null) {
                        if (taskDTO.getTaskState().equals(EntityState.Completed.toString()) ||
                                taskDTO.getTaskState().equals(EntityState.Incomplete.toString()) ||
                                taskDTO.getTaskState().equals(EntityState.Deleted.toString())) {
                            theTask.setTaskState(taskDTO.getTaskState());
                        } else {
                            throw new UnprocessableEntityException("Only Completed, Incomplete or Deleted is allowed as value for taskState!");
                        }
                    }

                    theTask.setUpdatedAt(Helper.getCurrentTimestamp());

                    try {
                        Task task = taskService.saveAndReturnTask(theTask);
                        return new ResponseEntity<>(task, HttpStatus.ACCEPTED);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                        throw new InternalServerErrorException("Something went wrong on the server!");
                    }
                } else {
                    throw new NotFoundException("No task with this taskRdbmsId is found!");
                }
            } else {
                throw new BadRequestException("No field present to update!");
            }
        } else {
            throw new BadRequestException("Please provide a taskRdbmsId with the request!");
        }

    }

    @DeleteMapping("/delete/by/task-id/{taskRdbmsId}")
    public ResponseEntity<Void> deleteTaskByTaskRdbmsId(@PathVariable("taskRdbmsId") Long taskRdbmsId) {

        if (taskRdbmsId != null) {
            Optional<Task> optionalTask = taskService.findTaskByTaskRdbmsId(taskRdbmsId);

            if (optionalTask.isPresent()) {
                Task task = optionalTask.get();

                if (task.getTaskState().equals(EntityState.Deleted.toString())) {
                    try {
                        taskService.deleteTaskByTaskRdbmsId(task.getTaskRdbmsId());
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                        throw new InternalServerErrorException("Something went wrong on the server!");
                    }
                } else {
                    throw new BadRequestException("This task can not be deleted permanently!");
                }
            } else {
                throw new NotFoundException("No task with this taskRdbmsId is found!");
            }
        } else {
            throw new BadRequestException("Please provide a taskRdbmsId with the request!");
        }

    }

    @GetMapping("/find/by/task-id/{taskRdbmsId}")
    public ResponseEntity<Task> findTaskByTaskRdbmsId(@PathVariable("taskRdbmsId") Long taskRdbmsId) {

        if (taskRdbmsId != null) {
            Optional<Task> optionalTask = taskService.findTaskByTaskRdbmsId(taskRdbmsId);

            if (optionalTask.isPresent()) {
                return new ResponseEntity<>(optionalTask.get(), HttpStatus.OK);
            } else {
                throw new NotFoundException("No task with this taskRdbmsId is found!");
            }
        } else {
            throw new BadRequestException("Please provide a taskRdbmsId with the request!");
        }

    }

    @GetMapping("/find/all/")
    public ResponseEntity<?> findAllTasks(@PageableDefault(size = MAX_PAGE_SIZE) Pageable pageable) {

        Page<Task> tasks = taskService.findAllTasks(pageable);

        if (tasks.getContent().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            long totalTasks = tasks.getTotalElements();
            int numberOfPageTasks = tasks.getNumberOfElements();

            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Total-Count", String.valueOf(totalTasks));

            if (numberOfPageTasks < totalTasks) {
                headers.add("first", Helper.buildPageUri(PageRequest.of(0, tasks.getSize())));
                headers.add("last", Helper.buildPageUri(PageRequest.of(tasks.getTotalPages() - 1, tasks.getSize())));

                if (tasks.hasNext()) {
                    headers.add("next", Helper.buildPageUri(tasks.nextPageable()));
                }

                if (tasks.hasPrevious()) {
                    headers.add("prev", Helper.buildPageUri(tasks.previousPageable()));
                }

                return new ResponseEntity<>(tasks.getContent(), headers, HttpStatus.PARTIAL_CONTENT);
            } else {
                return new ResponseEntity<>(tasks.getContent(), headers, HttpStatus.OK);
            }
        }

    }

}