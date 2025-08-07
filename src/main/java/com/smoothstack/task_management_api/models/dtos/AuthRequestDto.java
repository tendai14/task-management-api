package com.smoothstack.task_management_api.models.dtos;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String username;
    private String password;
}
