package com.springboot.simple.dto;

import lombok.Data;

@Data
public class TaskRequestDTO {
    private String title;
    private String description;
    private Boolean completed;
}
