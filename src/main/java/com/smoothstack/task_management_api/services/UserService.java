package com.smoothstack.task_management_api.services;

import com.smoothstack.task_management_api.models.dtos.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();
}
