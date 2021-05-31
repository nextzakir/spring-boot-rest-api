package com.nextzakir.restapi.dto;

import java.util.Objects;

public class TaskDTO {

    private String taskTitle;

    private String taskDescription;

    private String taskState;

    public TaskDTO() {
        super();
    }

    public TaskDTO(String taskTitle, String taskDescription, String taskState) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskState = taskState;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDTO taskDTO = (TaskDTO) o;
        return Objects.equals(taskTitle, taskDTO.taskTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskTitle);
    }

    @Override
    public String toString() {
        return "TaskDTO{" +
                "taskTitle='" + taskTitle + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskState='" + taskState + '\'' +
                '}';
    }

}