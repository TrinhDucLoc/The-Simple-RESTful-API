package com.springboot.simple.controller;

import com.springboot.simple.dto.TaskRequestDTO;
import com.springboot.simple.dto.TaskResponseDTO;
import com.springboot.simple.entity.User;
import com.springboot.simple.repository.UserRepository;
import com.springboot.simple.service.TaskService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasRole('MANAGER')")
    @ApiOperation("Create a new task")
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskRequestDTO taskRequestDTO) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();
            User user = userRepository.findByUsername(username);

            TaskResponseDTO createdTask = taskService.createTask(taskRequestDTO, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (Exception e) {
            throw e;
        }
    }

    @PreAuthorize("hasRole('MANAGER')")
    @ApiOperation("Update a task by ID")
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id, @RequestBody TaskRequestDTO taskRequestDTO) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();
            User user = userRepository.findByUsername(username);

            TaskResponseDTO updatedTask = taskService.updateTask(id, taskRequestDTO, user);
            return ResponseEntity.ok(updatedTask);
        } catch (Exception e) {
            throw e;
        }
    }

    @PreAuthorize("hasAnyRole('MEMBER', 'MANAGER')")
    @ApiOperation("Get a list of all tasks")
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        try {
            List<TaskResponseDTO> tasks = taskService.getAllTasks();
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            throw e;
        }
    }

    @PreAuthorize("hasAnyRole('MEMBER', 'MANAGER')")
    @ApiOperation("Get a single task by ID")
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        try {
            TaskResponseDTO task = taskService.getTaskById(id);
            return ResponseEntity.ok(task);
        } catch (Exception e) {
            throw e;
        }
    }

    @PreAuthorize("hasRole('MANAGER')")
    @ApiOperation("Delete a task by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw e;
        }
    }

    @PreAuthorize("hasAnyRole('MEMBER', 'MANAGER')")
    @ApiOperation(value = "Get tasks by user id")
    @GetMapping("/user")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByUserId() {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String username = userDetails.getUsername();
            User user = userRepository.findByUsername(username);
            Long userId = user.getId();

            List<TaskResponseDTO> tasks = taskService.getTasksByUserId(userId);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            throw e;
        }
    }
}
