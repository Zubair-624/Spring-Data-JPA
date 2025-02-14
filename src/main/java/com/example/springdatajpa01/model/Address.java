package com.example.springdatajpa01.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String Street;

    private String city;

    @OneToOne
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    private User user;
}
