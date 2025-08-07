package com.smoothstack.task_management_api.models.dtos;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String role;
    private String createdAt;
}
