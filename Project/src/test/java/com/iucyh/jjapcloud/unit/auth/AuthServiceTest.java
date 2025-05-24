package com.iucyh.jjapcloud.unit.auth;

import com.iucyh.jjapcloud.common.exception.ServiceException;
import com.iucyh.jjapcloud.common.exception.errorcode.ServiceErrorCode;
import com.iucyh.jjapcloud.domain.user.User;
import com.iucyh.jjapcloud.domain.user.repository.UserRepository;
import com.iucyh.jjapcloud.web.dto.RequestSuccessDto;
import com.iucyh.jjapcloud.web.dto.user.UserDto;
import com.iucyh.jjapcloud.web.service.auth.AuthService;
import com.iucyh.jjapcloud.web.service.auth.AuthServiceImpl;
import jakarta.servlet.http.HttpSession;
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
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpSession httpSession;

    private AuthService authService;

    private final String TEST_EMAIL = "test@example.com";
    private final String TEST_PASSWORD = "password123";
    private final String TEST_NICKNAME = "testUser";
    private final Integer TEST_ID = 1;

    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl(userRepository);
    }

    @Test
    @DisplayName("Login should return UserInfoDto when credentials are valid")
    void loginSuccess() {
        // Arrange
        User user = new User();
        user.setId(TEST_ID);
        user.setEmail(TEST_EMAIL);
        user.setPassword(TEST_PASSWORD);
        user.setNickname(TEST_NICKNAME);

        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(user));

        // Act
        UserDto result = authService.login(TEST_EMAIL, TEST_PASSWORD);

        // Assert
        assertNotNull(result);
        assertEquals(TEST_ID, result.getId());
        assertEquals(TEST_NICKNAME, result.getNickname());
        verify(userRepository, times(1)).findByEmail(TEST_EMAIL);
    }

    @Test
    @DisplayName("Login should throw ServiceException when user not found")
    void loginFailUserNotFound() {
        // Arrange
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.empty());

        // Act & Assert
        ServiceException exception = assertThrows(ServiceException.class, () -> {
            authService.login(TEST_EMAIL, TEST_PASSWORD);
        });

        assertEquals(ServiceErrorCode.UNAUTHORIZED, exception.getErrorCode());
        verify(userRepository, times(1)).findByEmail(TEST_EMAIL);
    }

    @Test
    @DisplayName("Login should throw ServiceException when password is incorrect")
    void loginFailWrongPassword() {
        // Arrange
        User user = new User();
        user.setId(TEST_ID);
        user.setEmail(TEST_EMAIL);
        user.setPassword("differentPassword");
        user.setNickname(TEST_NICKNAME);

        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(user));

        // Act & Assert
        ServiceException exception = assertThrows(ServiceException.class, () -> {
            authService.login(TEST_EMAIL, TEST_PASSWORD);
        });

        assertEquals(ServiceErrorCode.UNAUTHORIZED, exception.getErrorCode());
        verify(userRepository, times(1)).findByEmail(TEST_EMAIL);
    }

    @Test
    @DisplayName("Logout should invalidate session and return success message")
    void logoutSuccess() {
        // Act
        RequestSuccessDto result = authService.logout(httpSession);

        // Assert
        assertNotNull(result);
        assertEquals("Logout Success", result.getMessage());
        verify(httpSession, times(1)).invalidate();
    }
}