package com.example.banking.service;

import com.example.banking.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto registerUser(UserDto userDto);

    UserDto createUser(UserDto userDto);

    UserDto getUserById(Integer userId);

    UserDto updateUser(Integer userId, UserDto userDto);

    List<UserDto> getAllUsers();

    void deleteUser(Integer userId);
}
