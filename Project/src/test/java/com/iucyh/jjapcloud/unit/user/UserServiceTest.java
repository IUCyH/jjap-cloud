package com.iucyh.jjapcloud.unit.user;

import com.iucyh.jjapcloud.common.exception.ServiceException;
import com.iucyh.jjapcloud.common.exception.errorcode.ServiceErrorCode;
import com.iucyh.jjapcloud.domain.user.User;
import com.iucyh.jjapcloud.domain.user.repository.UserRepository;
import com.iucyh.jjapcloud.web.dto.IdDto;
import com.iucyh.jjapcloud.web.dto.user.CreateUserDto;
import com.iucyh.jjapcloud.web.dto.user.MyUserDto;
import com.iucyh.jjapcloud.web.dto.user.UpdateUserDto;
import com.iucyh.jjapcloud.web.dto.user.UserDto;
import com.iucyh.jjapcloud.web.service.user.UserService;
import com.iucyh.jjapcloud.web.service.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    private final Integer TEST_ID = 1;
    private final String TEST_EMAIL = "test@example.com";
    private final String TEST_PASSWORD = "password123";
    private final String TEST_NICKNAME = "testUser";

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    @DisplayName("getUserById should return UserDto when user exists")
    void getUserByIdSuccess() {
        // Arrange
        User user = new User();
        user.setId(TEST_ID);
        user.setEmail(TEST_EMAIL);
        user.setPassword(TEST_PASSWORD);
        user.setNickname(TEST_NICKNAME);

        when(userRepository.find(TEST_ID)).thenReturn(Optional.of(user));

        // Act
        UserDto result = userService.getUserById(TEST_ID);

        // Assert
        assertNotNull(result);
        assertEquals(TEST_ID, result.getId());
        assertEquals(TEST_NICKNAME, result.getNickname());
        verify(userRepository, times(1)).find(TEST_ID);
    }

    @Test
    @DisplayName("getUserById should throw ServiceException when user not found")
    void getUserByIdUserNotFound() {
        // Arrange
        when(userRepository.find(TEST_ID)).thenReturn(Optional.empty());

        // Act & Assert
        ServiceException exception = assertThrows(ServiceException.class, () -> {
            userService.getUserById(TEST_ID);
        });

        assertEquals(ServiceErrorCode.USER_NOT_FOUND, exception.getErrorCode());
        verify(userRepository, times(1)).find(TEST_ID);
    }

    @Test
    @DisplayName("getMyUserById should return MyUserDto when user exists")
    void getMyUserByIdSuccess() {
        // Arrange
        User user = new User();
        user.setId(TEST_ID);
        user.setEmail(TEST_EMAIL);
        user.setPassword(TEST_PASSWORD);
        user.setNickname(TEST_NICKNAME);

        when(userRepository.find(TEST_ID)).thenReturn(Optional.of(user));

        // Act
        MyUserDto result = userService.getMyUserById(TEST_ID);

        // Assert
        assertNotNull(result);
        assertEquals(TEST_ID, result.getId());
        assertEquals(TEST_NICKNAME, result.getNickname());
        assertEquals(TEST_EMAIL, result.getEmail());
        verify(userRepository, times(1)).find(TEST_ID);
    }

    @Test
    @DisplayName("getMyUserById should throw ServiceException when user not found")
    void getMyUserByIdUserNotFound() {
        // Arrange
        when(userRepository.find(TEST_ID)).thenReturn(Optional.empty());

        // Act & Assert
        ServiceException exception = assertThrows(ServiceException.class, () -> {
            userService.getMyUserById(TEST_ID);
        });

        assertEquals(ServiceErrorCode.USER_NOT_FOUND, exception.getErrorCode());
        verify(userRepository, times(1)).find(TEST_ID);
    }

    @Test
    @DisplayName("createUser should return IdDto with new user ID")
    void createUserSuccess() {
        // Arrange
        CreateUserDto createUserDto = new CreateUserDto();
        createUserDto.setNickname(TEST_NICKNAME);
        createUserDto.setEmail(TEST_EMAIL);
        createUserDto.setPassword(TEST_PASSWORD);

        when(userRepository.create(any(User.class))).thenReturn(TEST_ID);

        // Act
        IdDto result = userService.createUser(createUserDto);

        // Assert
        assertNotNull(result);
        assertEquals(TEST_ID, result.getId());
        verify(userRepository, times(1)).create(any(User.class));
    }

    @Test
    @DisplayName("updateUser should call repository update method")
    void updateUserSuccess() {
        // Arrange
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setNickname("newNickname");
        updateUserDto.setPassword("newPassword");

        // Act
        userService.updateUser(TEST_ID, updateUserDto);

        // Assert
        verify(userRepository, times(1)).update(eq(TEST_ID), any(User.class));
    }

    @Test
    @DisplayName("deleteUser should call repository delete method")
    void deleteUserSuccess() {
        // Act
        userService.deleteUser(TEST_ID);

        // Assert
        verify(userRepository, times(1)).delete(TEST_ID);
    }
}