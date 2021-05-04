package com.nextzakir.springbootrestapi.entity;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskRdbmsId;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 50)
    @Column(name = "task_title", unique = true)
    private String taskTitle;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 50)
    @Column(name = "task_slug", unique = true)
    private String taskSlug;

    @NotNull
    @NotBlank
    @Size(min = 10, max = 150)
    @Column(name = "task_description")
    private String taskDescription;

    @NotNull
    @NotBlank
    @Size(min = 5, max = 10)
    @Column(name = "task_state")
    private String taskState;

    @Nullable
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Nullable
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public Task() {
    }

    public Task(Long taskRdbmsId, String taskTitle, String taskSlug, String taskDescription, String taskState, Timestamp createdAt, Timestamp updatedAt) {
        this.taskRdbmsId = taskRdbmsId;
        this.taskTitle = taskTitle;
        this.taskSlug = taskSlug;
        this.taskDescription = taskDescription;
        this.taskState = taskState;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getTaskRdbmsId() {
        return taskRdbmsId;
    }

    public void setTaskRdbmsId(Long taskRdbmsId) {
        this.taskRdbmsId = taskRdbmsId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskSlug() {
        return taskSlug;
    }

    public void setTaskSlug(String taskSlug) {
        this.taskSlug = taskSlug;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskState() {
        return taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    @Nullable
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Nullable
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskRdbmsId.equals(task.taskRdbmsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskRdbmsId);
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskRdbmsId=" + taskRdbmsId +
                ", taskTitle='" + taskTitle + '\'' +
                ", taskSlug='" + taskSlug + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskState='" + taskState + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}