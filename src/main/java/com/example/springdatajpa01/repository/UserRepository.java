package com.example.springdatajpa01.repository;

import com.example.springdatajpa01.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUserEmail(String email);

}
