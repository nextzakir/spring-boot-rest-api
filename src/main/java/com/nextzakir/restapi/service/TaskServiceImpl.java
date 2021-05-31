package com.nextzakir.restapi.service;

import com.nextzakir.restapi.entity.Task;
import com.nextzakir.restapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public Task saveAndReturnTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> findTaskByTaskRdbmsId(Long taskRdbmsId) {
        return taskRepository.findById(taskRdbmsId);
    }

    @Override
    public Optional<Task> findTaskByTaskTitle(String taskTitle) {
        return taskRepository.findByTitle(taskTitle);
    }

    @Override
    public Optional<Task> findTaskByTaskSlug(String taskSlug) {
        return taskRepository.findBySlug(taskSlug);
    }

    @Override
    public Page<Task> findAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    @Override
    public Boolean taskTitleExists(String taskTitle) {
        return taskRepository.findByTitle(taskTitle).isPresent();
    }

    @Override
    public Long countTasks() {
        return taskRepository.count();
    }

    @Override
    public void deleteTaskByTaskRdbmsId(Long taskRdbmsId) {
        taskRepository.deleteById(taskRdbmsId);
    }

}