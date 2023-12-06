package com.springboot.simple.mapper;

import com.springboot.simple.dto.TaskRequestDTO;
import com.springboot.simple.dto.TaskResponseDTO;
import com.springboot.simple.entity.Task;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    private final ModelMapper modelMapper;

    public TaskMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TaskResponseDTO convertToResponseDTO(Task task) {
        return modelMapper.map(task, TaskResponseDTO.class);
    }

    public Task convertToEntity(TaskRequestDTO taskRequestDTO) {
        return modelMapper.map(taskRequestDTO, Task.class);
    }
}
