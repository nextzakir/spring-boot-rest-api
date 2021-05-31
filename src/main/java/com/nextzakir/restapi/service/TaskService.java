package com.nextzakir.restapi.service;

import com.nextzakir.restapi.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TaskService {

    void saveTask(Task task);
    Task saveAndReturnTask(Task task);

    Optional<Task> findTaskByTaskRdbmsId(Long taskRdbmsId);
    Optional<Task> findTaskByTaskTitle(String taskTitle);
    Optional<Task> findTaskByTaskSlug(String taskSlug);
    Page<Task> findAllTasks(Pageable pageable);

    Boolean taskTitleExists(String taskTitle);

    Long countTasks();

    void deleteTaskByTaskRdbmsId(Long taskRdbmsId);

}