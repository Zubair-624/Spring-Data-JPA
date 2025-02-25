package com.example.springdatajpa01;

import com.example.springdatajpa01.dto.UserDto;
import com.example.springdatajpa01.model.Address;
import com.example.springdatajpa01.model.User;
import com.example.springdatajpa01.repository.UserRepository;
import com.example.springdatajpa01.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp(){

        Address address = Address.builder()
                .street("123 Main st")
                .state("NY")
                .city("New York")
                .country("USA")
                .zipCode("10001")
                .build();

        user = User.builder()
                .userId(1L)
                .userName("Zubair Mazumder")
                .userEmail("zubair@gmail.com")
                .userPhoneNumber("+08801903123131")
                .address(address)
                .build();

        userDto = UserDto.builder()
                .userId(1L)
                .userName("Zubair Mazumder")
                .userEmail("zubair@gmail.com")
                .userPhoneNumber("+08801903123131")
                .street("123 Main st")
                .state("NY")
                .city("New York")
                .country("USA")
                .zipCode("10001")
                .build();
    }

    /** -------------------- Test: Create User -------------------- **/

    @Test
    void shouldCreateUserSuccessfully(){

        when(userRepository.existsByUserEmail(userDto.getUserEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto createdUser = userService.createUser(userDto);

        assertNotNull(createdUser);
        assertEquals(userDto.getUserEmail(), createdUser.getUserEmail());

        verify(userRepository, times(1)).save(any(User.class));

    }

    @Test
    void shouldThrowExceptionWhenUserEmailIsAlreadyExist() {

        when(userRepository.existsByUserEmail(userDto.getUserEmail())).thenReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userService.createUser(userDto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Email already exists", exception.getReason());


    }

    /** -------------------- Test: Get User By ID -------------------- **/

    @Test
    void shouldReturnUserByUserId(){
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDto foundUser = userService.getUserByUserId(1L);

        assertNotNull(foundUser);
        assertEquals(user.getUserId(), foundUser.getUserId());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundByUserId(){
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userService.getUserByUserId(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("User not found", exception.getReason());
    }

    /** -------------------- Test: Get All Users -------------------- **/

    @Test
    void shouldReturnAllUsers(){
        List<User> userList = Collections.singletonList(user);
        Page<User> userPage = new PageImpl<>(userList);

        when(userRepository.findAll(any(PageRequest.class))).thenReturn(userPage);

        Page<UserDto> allUserResult = userService.getAllUsers(0, 10);

        assertFalse(allUserResult.isEmpty());
        assertEquals(1, allUserResult.getTotalElements());
        verify(userRepository, times(1)).findAll(any(PageRequest.class));
    }

    /** -------------------- Test: Update User -------------------- **/

    @Test
    void shouldUpdateUserSuccessfully(){
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto updatedUser = userService.updateUserByUserId(1L, userDto);

        assertNotNull(updatedUser);
        assertEquals(user.getUserId(), updatedUser.getUserId());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingUserNotExist(){
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userService.updateUserByUserId(1L, userDto);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("User not found", exception.getReason());
    }

    /** -------------------- Test: Delete User -------------------- **/

    @Test
    void shouldDeleteUserSuccessfully(){
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUserByUserId(1L);

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void shouldThrowExceptionWhenDeletingUserNotExist(){
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userService.deleteUserByUserId(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("User not found", exception.getReason());
    }


}
