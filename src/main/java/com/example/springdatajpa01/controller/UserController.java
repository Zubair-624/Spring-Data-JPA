package com.example.springdatajpa01.controller;

import com.example.springdatajpa01.dto.UserDto;
import com.example.springdatajpa01.model.User;
import com.example.springdatajpa01.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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








}
