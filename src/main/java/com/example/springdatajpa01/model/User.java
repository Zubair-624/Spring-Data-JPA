package com.example.springdatajpa01.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String userName;

    @Email(message = "Email should be valid")
    @Column(unique = true, nullable = false)
    private String userEmail;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]\\d{1,14}$", message = "Phone number must be exactly 15 digits")
    @Column(unique = true, nullable = false)
    private String userPhoneNumber;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id_fk", referencedColumnName = "id", nullable = false)
    private Address address;
}
