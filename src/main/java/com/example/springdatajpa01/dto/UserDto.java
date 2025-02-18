package com.example.springdatajpa01.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long userId;

    @NotBlank(message = "Name is required")
    private String userName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String userEmail;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]\\d{1,14}$", message = "Phone number must be between 10-15 digits")
    private String userPhoneNumber;

    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}
