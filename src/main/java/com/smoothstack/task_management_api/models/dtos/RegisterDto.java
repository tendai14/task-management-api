package com.smoothstack.task_management_api.models.dtos;

import lombok.Data;

@Data
public class RegisterDto {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
