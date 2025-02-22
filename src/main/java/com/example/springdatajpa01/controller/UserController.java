package com.example.springdatajpa01.controller;

import com.example.springdatajpa01.dto.UserDto;
import com.example.springdatajpa01.model.User;
import com.example.springdatajpa01.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**-------------------- Create User --------------------**/
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        log.info("Received request to create a user: {}", userDto.getUserEmail());

        UserDto createdNewUser = userService.createUser(userDto);
        return ResponseEntity.status(201).body(createdNewUser);
    }

    /**-------------------- Get User By ID --------------------**/
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserByUserId(@PathVariable Long userId){
        log.info("Fetching user with ID: {}", userId);

        UserDto user = userService.getUserByUserId(userId);
        return ResponseEntity.ok(user);
    }

    /**-------------------- Get All Users (Pagination & Sorting) --------------------**/
    @GetMapping
    public ResponseEntity<Page<UserDto>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        log.info("Fetching all users - Page: {}, Size: {}", page, size);
        Page<UserDto> allUsers = userService.getAllUsers(page, size);
        return ResponseEntity.ok(allUsers);
    }

    /**-------------------- Update User --------------------**/
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUserByUserId(
            @PathVariable Long userId,
            @Valid @RequestBody UserDto userDto
    ){
        log.info("Updating user with ID: {}", userId);
        UserDto updatedUser = userService.updateUserByUserId(userId, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    /**-------------------- Delete User --------------------**/
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserByUserId(@PathVariable Long userId){
        log.info("Deleting user with ID: {}", userId);

        userService.deleteUserByUserId(userId);
        return ResponseEntity.noContent().build();
    }














}
