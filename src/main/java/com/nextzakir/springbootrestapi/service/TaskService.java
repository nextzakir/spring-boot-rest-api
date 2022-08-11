package com.nextzakir.springbootrestapi.service;

import com.nextzakir.springbootrestapi.dto.TaskDTO;
import com.nextzakir.springbootrestapi.entity.Task;
import com.nextzakir.springbootrestapi.helper.Response;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TaskService {

    void saveTask(Task task);

    Task saveAndReturnTask(TaskDTO taskDTO);

    void deleteTaskByTaskRdbmsId(Long taskRdbmsId);

    Optional<Task> findTaskByTaskRdbmsId(Long taskRdbmsId);

    Response findAllTasks(Pageable pageable);

    Boolean taskTitleExists(String taskTitle);

}