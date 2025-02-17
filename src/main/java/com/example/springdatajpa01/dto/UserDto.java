package com.example.springdatajpa01.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]\\d{1,14}", message = "Phone number must be between 10-15 digits")
    private String phoneNumber;

    private String street;
    private String city;
    private String state;
    private String country;
}
