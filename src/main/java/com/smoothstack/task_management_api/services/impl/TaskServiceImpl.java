package com.smoothstack.task_management_api.services.impl;

import com.smoothstack.task_management_api.enums.TaskStatus;
import com.smoothstack.task_management_api.models.entities.Task;
import com.smoothstack.task_management_api.repositories.TaskRepository;
import com.smoothstack.task_management_api.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public List<Task> getAllTasks(TaskStatus status, Long assigneeId) {
        if (status != null && assigneeId != null) {
            return taskRepository.findByStatusAndAssigneeId(status, assigneeId);
        } else if (status != null) {
            return taskRepository.findByStatus(status);
        } else if (assigneeId != null) {
            return taskRepository.findByAssigneeId(assigneeId);
        } else {
            return taskRepository.findAll();
        }
    }

    @Override
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    task.setStatus(updatedTask.getStatus());
                    task.setPriority(updatedTask.getPriority());
                    task.setAssigneeId(updatedTask.getAssigneeId());
                    return taskRepository.save(task);
                });
    }

    @Override
    public boolean deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
