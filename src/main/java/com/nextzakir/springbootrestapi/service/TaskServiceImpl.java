package com.nextzakir.springbootrestapi.service;

import com.nextzakir.springbootrestapi.dto.TaskDTO;
import com.nextzakir.springbootrestapi.entity.Task;
import com.nextzakir.springbootrestapi.exception.InternalServerErrorException;
import com.nextzakir.springbootrestapi.helper.EntityState;
import com.nextzakir.springbootrestapi.helper.Helper;
import com.nextzakir.springbootrestapi.helper.Response;
import com.nextzakir.springbootrestapi.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private HelperService helperService;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void saveTask(Task task) {
        try {
            taskRepository.save(task);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new InternalServerErrorException("Something went wrong on the server!");
        }
    }

    @Override
    public Task saveAndReturnTask(TaskDTO taskDTO) {
        Task theTask = new Task();
        theTask.setTaskTitle(taskDTO.getTaskTitle());
        theTask.setTaskSlug(Helper.toSlug(taskDTO.getTaskTitle()));
        theTask.setTaskDescription(taskDTO.getTaskDescription());
        theTask.setTaskState(EntityState.Incomplete.toString());
        theTask.setCreatedAt(Helper.getCurrentTimestamp());
        theTask.setUpdatedAt(Helper.getCurrentTimestamp());

        try {
            return taskRepository.save(theTask);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new InternalServerErrorException("Something went wrong on the server!");
        }
    }

    @Override
    public Optional<Task> findTaskByTaskRdbmsId(Long taskRdbmsId) {
        return taskRepository.findById(taskRdbmsId);
    }

    @Override
    public Response findAllTasks(Pageable pageable) {
        return helperService.getResponse(taskRepository.findAll(pageable), null, null);
    }

    @Override
    public Boolean taskTitleExists(String taskTitle) {
        return taskRepository.findByTitle(taskTitle).isPresent();
    }

    @Override
    public void deleteTaskByTaskRdbmsId(Long taskRdbmsId) {
        try {
            taskRepository.deleteById(taskRdbmsId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new InternalServerErrorException("Something went wrong on the server!");
        }
    }

}