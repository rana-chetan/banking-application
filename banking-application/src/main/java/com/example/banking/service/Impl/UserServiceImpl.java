package com.example.banking.service.Impl;

import com.example.banking.dto.UserDto;
import com.example.banking.entity.Role;
import com.example.banking.entity.User;
import com.example.banking.repository.RoleRepository;
import com.example.banking.repository.UserRepository;
import com.example.banking.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDto registerUser(UserDto userDto) {

        User user = this.modelMapper.map(userDto, User.class);
        user.setUsername(userDto.getUsername());
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword())); // Encrypt the password

        //Role assignment
        Role defaultRole = roleRepository.findById(502).get();
        user.getRoles().add(defaultRole);

        User save = userRepository.save(user);
        return this.modelMapper.map(save, UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        User user = modelMapper.map(userDto, User.class);
        User save = userRepository.save(user);
        return modelMapper.map(save, UserDto.class);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User byId = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return this.modelMapper.map(byId, UserDto.class);
    }

    @Override
    public UserDto updateUser(Integer userId, UserDto userDto) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());

        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> all = userRepository.findAll();
        List<UserDto> list = all.stream().map((user) -> modelMapper.map(user, UserDto.class)).toList();
        return list;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        userRepository.delete(user);
    }
}
