package com.smoothstack.task_management_api.services.impl;


import com.smoothstack.task_management_api.models.dtos.UserDto;
import com.smoothstack.task_management_api.models.entities.User;
import com.smoothstack.task_management_api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.modelmapper.ModelMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setFirstName("Test User");
        user.setLastName("test@example.com");
        user.setPassword("secret");

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("Test User");
        userDto.setLastName("test@example.com");
    }

    @Test
    void findAll_shouldReturnMappedUserDtos() {

        when(userRepository.findAll()).thenReturn(List.of(user));
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);


        List<UserDto> result = userService.findAll();


        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getFirstName()).isEqualTo("Test User");
        assertThat(result.get(0).getLastName()).isEqualTo("test@example.com");

        verify(userRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(user, UserDto.class);
    }
}
