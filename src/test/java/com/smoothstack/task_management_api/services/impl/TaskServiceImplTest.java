package com.smoothstack.task_management_api.services.impl;

import com.smoothstack.task_management_api.enums.TaskPriority;
import com.smoothstack.task_management_api.enums.TaskStatus;
import com.smoothstack.task_management_api.models.entities.Task;
import com.smoothstack.task_management_api.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        task = Task.builder()
                .id(1L)
                .title("Test Task")
                .description("Test Description")
                .status(TaskStatus.TODO)
                .priority(TaskPriority.valueOf("HIGH"))
                .assigneeId(10L)
                .creatorId(1L)
                .build();
    }

    @Test
    void getAllTasks_whenNoFilters_thenReturnsAllTasks() {
        List<Task> tasks = List.of(task);
        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.getAllTasks(null, null);

        assertThat(result).hasSize(1);
        verify(taskRepository).findAll();
    }

    @Test
    void getAllTasks_whenFilterByStatus_thenReturnsFiltered() {
        when(taskRepository.findByStatus(TaskStatus.TODO)).thenReturn(List.of(task));

        List<Task> result = taskService.getAllTasks(TaskStatus.TODO, null);

        assertThat(result).containsExactly(task);
        verify(taskRepository).findByStatus(TaskStatus.TODO);
    }

    @Test
    void getAllTasks_whenFilterByAssignee_thenReturnsFiltered() {
        when(taskRepository.findByAssigneeId(10L)).thenReturn(List.of(task));

        List<Task> result = taskService.getAllTasks(null, 10L);

        assertThat(result).containsExactly(task);
        verify(taskRepository).findByAssigneeId(10L);
    }

    @Test
    void getAllTasks_whenFilterByStatusAndAssignee_thenReturnsFiltered() {
        when(taskRepository.findByStatusAndAssigneeId(TaskStatus.TODO, 10L)).thenReturn(List.of(task));

        List<Task> result = taskService.getAllTasks(TaskStatus.TODO, 10L);

        assertThat(result).containsExactly(task);
        verify(taskRepository).findByStatusAndAssigneeId(TaskStatus.TODO, 10L);
    }

    @Test
    void getTaskById_whenExists_thenReturnTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<Task> result = taskService.getTaskById(1L);

        assertThat(result).isPresent().contains(task);
        verify(taskRepository).findById(1L);
    }

    @Test
    void getTaskById_whenNotExists_thenReturnEmpty() {
        when(taskRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Task> result = taskService.getTaskById(2L);

        assertThat(result).isEmpty();
    }

    @Test
    void createTask_shouldSaveAndReturnTask() {
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.createTask(task);

        assertThat(result).isEqualTo(task);
        verify(taskRepository).save(task);
    }

    @Test
    void updateTask_whenTaskExists_thenUpdateAndReturn() {
        Task updated = Task.builder()
                .title("Updated")
                .description("Updated Desc")
                .status(TaskStatus.IN_PROGRESS)
                .priority(TaskPriority.valueOf("LOW"))
                .assigneeId(20L)
                .build();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenAnswer(i -> i.getArgument(0));

        Optional<Task> result = taskService.updateTask(1L, updated);

        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Updated");
        assertThat(result.get().getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void updateTask_whenTaskNotExists_thenReturnEmpty() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Task> result = taskService.updateTask(99L, task);

        assertThat(result).isEmpty();
    }

    @Test
    void deleteTask_whenExists_thenDeletesAndReturnsTrue() {
        when(taskRepository.existsById(1L)).thenReturn(true);

        boolean result = taskService.deleteTask(1L);

        assertThat(result).isTrue();
        verify(taskRepository).deleteById(1L);
    }

    @Test
    void deleteTask_whenNotExists_thenReturnsFalse() {
        when(taskRepository.existsById(999L)).thenReturn(false);

        boolean result = taskService.deleteTask(999L);

        assertThat(result).isFalse();
        verify(taskRepository, never()).deleteById(anyLong());
    }
}
