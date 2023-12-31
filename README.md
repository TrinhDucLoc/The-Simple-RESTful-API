---
Author: Trinh Duc Loc
Title: The Simple RESTful API
---

# Simple RESTful API

This repository contains a simple RESTful API built with [Spring Boot](https://spring.io/projects/spring-boot). The API provides basic functionality for managing tasks, such as creating, updating, and deleting tasks.

## Table of Contents
- [Features](#features)
- [Getting Started](#getting-started)
- [Validate](#Validate)
- [API Endpoints](#api-endpoints)


## Features

1. **Task Management:**
    - User can create a new task.
    - User can update a task by ID.
    - User can get a list of all tasks.
    - User can get a single task by ID.
    - User can delete a task by ID.
    - User can get a list tasks created by user.

2. **User Authentication and Authorization:**
    - Users can register with roles: member and manager.
    - Users can log in with registered credentials.
    - Authorization ensures specific actions are allowed based on user roles.

## Getting Started

### Prerequisites

Before running the project, make sure you have the following installed:

- Install Java JDK 8.
- Install IntelliJ IDEA or other IDEA.
- Install MySQL Server 8 and MySQL Workbench.

### Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/TrinhDucLoc/The-Simple-RESTful-API
    cd The-Simple-RESTful-API
    ```

2. Build and install:

    ```bash
    mvn clean install
    ```

### Connect Database with MySQL
Click application-local.properties
- spring.datasource.url = jdbc:mysql://localhost:3306/db_simple_api
- spring.datasource.username = ${YOUR_USERNAME:}
- spring.datasource.password = ${YOUR_PASSWORD:}

### Running the Application

- Run the application by executing the `main` function of `SpringbootSimpleApplication`.
- The application will start at http://localhost:8080.
- Link Swagger API Document: http://localhost:8080/swagger-ui/

### Add 2 role: ROLE_MANAGER and ROLE_MEMBER into roles table
- Way 1: Run RoleServiceTest to create 2 role
- Way 2: Add role into database


## Validate
### Validation for TaskRequestDTO

The `TaskRequestDTO` object is used for creating or updating tasks. It follows the following validation rules:

- **Title:**
   - Cannot be blank or null.
   - Maximum length is 255 characters.

- **Description:**
   - Cannot be blank or null.
   - Maximum length is 1000 characters.

- **Completed:**
   - Cannot be null.

Use this object when creating or updating tasks through the API.

### Validation for RegisterRequest

The `RegisterRequest` object is used for registering a new user. It adheres to the following validation guidelines:

- **Username:**
   - Cannot be blank or null.

- **Email:**
   - Cannot be blank or null.
   - Must follow a valid email format.

- **Password:**
   - Cannot be blank or null.

## API Endpoints
- Swagger API Document
  
![Swagger API Document](https://raw.githubusercontent.com/TrinhDucLoc/The-Simple-RESTful-API/main/image/SimpleAPI_001.png)

- Auth Controller

![Auth Controller](https://raw.githubusercontent.com/TrinhDucLoc/The-Simple-RESTful-API/main/image/SimpleAPI_002.png)

- Task Controller

![Task Controller](https://raw.githubusercontent.com/TrinhDucLoc/The-Simple-RESTful-API/main/image/SimpleAPI_003.png)

Authorize API Task Controller:
- ROLE_MANAGER: Full access rights
- ROLE_MEMBER: No access rights to Create, UPDATE, DELETE
