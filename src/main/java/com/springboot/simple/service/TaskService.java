package com.springboot.simple.service;

import com.springboot.simple.dto.TaskRequestDTO;
import com.springboot.simple.dto.TaskResponseDTO;
import com.springboot.simple.entity.User;

import java.util.List;

public interface TaskService {
    TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO, User user);
    TaskResponseDTO updateTask(Long id, TaskRequestDTO updatedTaskRequestDTO, User user);
    List<TaskResponseDTO> getAllTasks();
    TaskResponseDTO getTaskById(Long id);
    void deleteTask(Long id);
    List<TaskResponseDTO> getTasksByUserId(Long userId);
}
