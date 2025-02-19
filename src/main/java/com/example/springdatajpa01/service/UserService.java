package com.example.springdatajpa01.service;

import com.example.springdatajpa01.dto.UserDto;
import com.example.springdatajpa01.model.Address;
import com.example.springdatajpa01.model.User;
import com.example.springdatajpa01.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;


    //-------------------- CRUD Operations --------------------//

    /** ----- Create Operation ----- **/
    @Transactional
    public UserDto createUser(UserDto userDto){
        log.info("Creating user: {}", userDto.getUserEmail());

        if(userRepository.existsByUserEmail(userDto.getUserEmail())){
            log.error("Email already exit: {}", userDto.getUserEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exit");
        }

        Address address = Address.builder()
                .street(userDto.getStreet())
                .state(userDto.getState())
                .city(userDto.getCity())
                .country(userDto.getCountry())
                .zipCode(userDto.getZipCode())
                .build();

        User user = User.builder()
                .userName(userDto.getUserName())
                .userEmail(userDto.getUserEmail())
                .userPhoneNumber(userDto.getUserPhoneNumber())
                .address(address)
                .build();

        User savedUser = userRepository.save(user);
        log.info("User created with id: {} ", savedUser.getUserId());

        return mapToDTO(savedUser);
    }

    private UserDto mapToDTO(User user){
        return UserDto.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .userEmail(user.getUserEmail())
                .userPhoneNumber(user.getUserPhoneNumber())
                .street(user.getAddress().getStreet())
                .state(user.getAddress().getState())
                .city(user.getAddress().getCity())
                .zipCode(user.getAddress().getZipCode())
                .build();
    }






}
