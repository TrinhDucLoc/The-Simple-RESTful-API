package com.springboot.simple.service.impl;

import com.springboot.simple.dto.TaskRequestDTO;
import com.springboot.simple.dto.TaskResponseDTO;
import com.springboot.simple.entity.Task;
import com.springboot.simple.entity.User;
import com.springboot.simple.exception.TaskNotFoundException;
import com.springboot.simple.mapper.TaskMapper;
import com.springboot.simple.repository.TaskRepository;
import com.springboot.simple.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;
    private TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO, User user) {
        Task task = taskMapper.convertToEntity(taskRequestDTO);
        task.setUser(user);

        Task savedTask = taskRepository.save(task);
        return taskMapper.convertToResponseDTO(savedTask);
    }

    @Override
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO updatedTaskRequestDTO, User user) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with ID " + id + " not found"));

        Task updatedTask = taskMapper.convertToEntity(updatedTaskRequestDTO);
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setCompleted(updatedTask.getCompleted());

        existingTask.setUser(user);

        Task savedTask = taskRepository.save(existingTask);
        return taskMapper.convertToResponseDTO(savedTask);
    }

    @Override
    public List<TaskResponseDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponseDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with ID " + id + " not found"));
        return taskMapper.convertToResponseDTO(task);
    }

    @Override
    public void deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else {
            throw new TaskNotFoundException("Task with ID " + id + " not found");
        }
    }

    @Override
    public List<TaskResponseDTO> getTasksByUserId(Long userId) {
        List<Task> tasks = taskRepository.findByUserId(userId);
        return tasks.stream()
                .map(taskMapper::convertToResponseDTO)
                .collect(Collectors.toList());
    }
}
