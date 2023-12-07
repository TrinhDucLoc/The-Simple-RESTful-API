package com.springboot.simple.service;

import com.springboot.simple.entity.Role;
import com.springboot.simple.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoleServiceTest {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    void createRoles() {
        createRole("ROLE_MANAGER");
        createRole("ROLE_MEMBER");
    }

    private void createRole(String roleName) {
        Role role = new Role();
        role.setName(roleName);
        roleRepository.save(role);
    }
}
