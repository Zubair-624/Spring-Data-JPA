package com.example.springdatajpa01.service;

import com.example.springdatajpa01.dto.UserDto;
import com.example.springdatajpa01.model.Address;
import com.example.springdatajpa01.model.User;
import com.example.springdatajpa01.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //-------------------- CRUD OPERATIONS --------------------//

    /**
     * -------------------- Create Operation --------------------
     **/
    @Transactional
    public UserDto createUser(UserDto userDto) {
        log.info("Creating user: {}", userDto.getUserEmail());

        if (userRepository.existsByUserEmail(userDto.getUserEmail())) {
            log.error("Email already exists: {}", userDto.getUserEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
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
        log.info("User created with ID: {} ", savedUser.getUserId());

        return mapToDTO(savedUser);
    }

    /**
     * -------------------- Read by ID Operation --------------------
     **/
    @Transactional(readOnly = true)
    public UserDto getUserByUserId(Long userId) {
        log.info("Fetching user with ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User with ID: {} not found.", userId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
                });

        log.info("User with ID: {} retrieved successfully", userId);
        return mapToDTO(user);
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
                .country(user.getAddress().getCountry())
                .zipCode(user.getAddress().getZipCode())
                .build();
    }

}
