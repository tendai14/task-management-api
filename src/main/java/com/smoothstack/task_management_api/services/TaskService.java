package com.smoothstack.task_management_api.services;

import com.smoothstack.task_management_api.enums.TaskStatus;
import com.smoothstack.task_management_api.models.entities.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> getAllTasks(TaskStatus status, Long assigneeId);
    Optional<Task> getTaskById(Long id);
    Task createTask(Task task);
    Optional<Task> updateTask(Long id, Task task);
    boolean deleteTask(Long id);
}
