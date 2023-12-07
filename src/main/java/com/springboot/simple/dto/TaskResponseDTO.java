package com.springboot.simple.dto;

import lombok.Data;

@Data
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Boolean completed;
    private Long userId;
}
