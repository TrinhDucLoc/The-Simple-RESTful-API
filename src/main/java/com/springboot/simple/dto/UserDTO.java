package com.springboot.simple.dto;

import lombok.Data;

@Data
public class UserDTO {
    private long id;
    private String username;
    private String email;
}
