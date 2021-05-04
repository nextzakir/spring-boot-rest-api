package com.nextzakir.springbootrestapi.repository;

import com.nextzakir.springbootrestapi.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "SELECT * FROM tasks WHERE title = ?1", nativeQuery = true)
    Optional<Task> findByTitle(String title);

    @Query(value = "SELECT * FROM tasks WHERE slug = ?1", nativeQuery = true)
    Optional<Task> findBySlug(String slug);

}