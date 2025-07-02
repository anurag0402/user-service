package com.telus.userservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telus.userservice.dto.UserDTO;
import com.telus.userservice.dto.mapper.UserMapper;
import com.telus.userservice.model.User;
import com.telus.userservice.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create user
    public UserDTO createUser(UserDTO dto) {
        User user = UserMapper.toEntity(dto);
        User savedUser = userRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }

    // Get user by ID
    public Optional<UserDTO> getUserById(String id) {
        return userRepository.findById(id)
                .map(UserMapper::toDTO);
    }

    // Get all users
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Update user
    public Optional<UserDTO> updateUser(String id, UserDTO dto) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setUsername(dto.getUsername());
            existingUser.setEmail(dto.getEmail());
            existingUser.setPassword(dto.getPassword());
            existingUser.setRole(User.Role.valueOf(dto.getRole()));
            User updatedUser = userRepository.save(existingUser);
            return UserMapper.toDTO(updatedUser);
        });
    }

    // Delete user
    public boolean deleteUser(String id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return true;
        }).orElse(false);
    }
} 