package com.nextzakir.springbootrestapi.controller;

import com.nextzakir.springbootrestapi.dto.TaskDTO;
import com.nextzakir.springbootrestapi.entity.Task;
import com.nextzakir.springbootrestapi.exception.BadRequestException;
import com.nextzakir.springbootrestapi.exception.ConflictException;
import com.nextzakir.springbootrestapi.exception.NotFoundException;
import com.nextzakir.springbootrestapi.exception.UnprocessableEntityException;
import com.nextzakir.springbootrestapi.helper.EntityState;
import com.nextzakir.springbootrestapi.helper.Helper;
import com.nextzakir.springbootrestapi.helper.Response;
import com.nextzakir.springbootrestapi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    private static final int MAX_PAGE_SIZE = 20;

    @PostMapping("/")
    public ResponseEntity<Task> store(@RequestBody TaskDTO taskDTO) {

        if (taskDTO.getTaskTitle() == null || taskDTO.getTaskTitle().isBlank()) {
            throw new UnprocessableEntityException("Task title is required!");
        }

        if (taskDTO.getTaskTitle().length() > 50) {
            throw new UnprocessableEntityException("Task title must not be more than 50 characters!");
        }

        if (taskDTO.getTaskDescription() == null || taskDTO.getTaskDescription().isBlank()) {
            throw new UnprocessableEntityException("Task description is required!");
        }

        if (taskDTO.getTaskDescription().length() > 150) {
            throw new UnprocessableEntityException("Task description must not be more than 150 characters!");
        }

        if (taskService.taskTitleExists(taskDTO.getTaskTitle())) {
            throw new ConflictException("A task with this title already exists!");
        }
        
        return new ResponseEntity<>(taskService.saveAndReturnTask(taskDTO), HttpStatus.CREATED);

    }

    @PutMapping("/{taskRdbmsId}")
    public ResponseEntity<Task> update(@PathVariable("taskRdbmsId") Long taskRdbmsId, @RequestBody TaskDTO taskDTO) {

        if (taskRdbmsId == null) {
            throw new BadRequestException("Please provide a taskRdbmsId with the request!");
        }

        if (taskDTO.getTaskTitle() == null && taskDTO.getTaskDescription() == null && taskDTO.getTaskState() == null) {
            throw new BadRequestException("No field present to update!");
        }

        Task task = taskService.findTaskByTaskRdbmsId(taskRdbmsId)
                .orElseThrow(() -> new NotFoundException("No task with this taskRdbmsId is found!"));

        if (taskDTO.getTaskTitle() != null && ! taskDTO.getTaskTitle().isBlank()) {
            if (taskDTO.getTaskTitle().length() > 50) {
                throw new UnprocessableEntityException("Task title must not be more than 50 characters!");
            }

            task.setTaskTitle(taskDTO.getTaskTitle());
            task.setTaskSlug(Helper.toSlug(taskDTO.getTaskTitle()));
        }

        if (taskDTO.getTaskDescription() != null && ! taskDTO.getTaskDescription().isBlank()) {
            if (taskDTO.getTaskDescription().length() > 150) {
                throw new UnprocessableEntityException("Task description must not be more than 150 characters!");
            }

            task.setTaskDescription(taskDTO.getTaskDescription());
        }

        if (taskDTO.getTaskState() != null) {
            if (! taskDTO.getTaskState().equals(EntityState.Completed.toString()) &&
                    ! taskDTO.getTaskState().equals(EntityState.Incomplete.toString()) &&
                    ! taskDTO.getTaskState().equals(EntityState.Deleted.toString())) {
                throw new UnprocessableEntityException("Only Completed, Incomplete or Deleted is allowed as value for taskState!");
            }

            task.setTaskState(taskDTO.getTaskState());
        }

        task.setUpdatedAt(Helper.getCurrentTimestamp());

        taskService.saveTask(task);
        return new ResponseEntity<>(task, HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/{taskRdbmsId}")
    public ResponseEntity<Void> destroy(@PathVariable("taskRdbmsId") Long taskRdbmsId) {

        if (taskRdbmsId == null) {
            throw new BadRequestException("Please provide a taskRdbmsId with the request!");
        }

        Task task = taskService.findTaskByTaskRdbmsId(taskRdbmsId)
                .orElseThrow(() -> new NotFoundException("No task with this taskRdbmsId is found!"));

        if (! task.getTaskState().equals(EntityState.Deleted.toString())) {
            throw new BadRequestException("This task can not be deleted permanently!");
        }

        taskService.deleteTaskByTaskRdbmsId(task.getTaskRdbmsId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping("/{taskRdbmsId}")
    public ResponseEntity<Task> show(@PathVariable("taskRdbmsId") Long taskRdbmsId) {

        if (taskRdbmsId == null) {
            throw new BadRequestException("Please provide a taskRdbmsId with the request!");
        }

        return new ResponseEntity<>(taskService.findTaskByTaskRdbmsId(taskRdbmsId)
                    .orElseThrow(() -> new NotFoundException("No task with this taskRdbmsId is found!")), HttpStatus.OK);

    }

    @GetMapping("/")
    @SuppressWarnings("unchecked") // casting is type safe
    public ResponseEntity<List<Task>> index(@PageableDefault(size = MAX_PAGE_SIZE) Pageable pageable) {

        Response response = taskService.findAllTasks(pageable);

        if (response.getContents() == null && response.getHttpHeaders() == null) {
            return new ResponseEntity<>(response.getHttpStatusCode());
        }

        return new ResponseEntity<>((List<Task>) response.getContents(), response.getHttpHeaders(), response.getHttpStatusCode());

    }

}