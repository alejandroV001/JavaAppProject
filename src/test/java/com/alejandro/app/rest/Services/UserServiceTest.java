package com.alejandro.app.rest.Services;

import com.alejandro.app.rest.Models.User;
import com.alejandro.app.rest.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testRegisterUser() {
        User registeredUser = new User();
        registeredUser.setEmail("test@example.com");
        registeredUser.setPassword("password");

        when(userRepository.findByEmail(registeredUser.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(registeredUser);

        User result = userService.registerUser(registeredUser);

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        assertEquals("password", result.getPassword());

        verify(userRepository, times(1)).findByEmail(registeredUser.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUserWithEmailAlreadyExists() {
        User registeredUser = new User();
        registeredUser.setEmail("existing@example.com");

        when(userRepository.findByEmail(registeredUser.getEmail())).thenReturn(Optional.of(registeredUser));

        assertThrows(RuntimeException.class, () -> userService.registerUser(registeredUser));

        verify(userRepository, times(1)).findByEmail(registeredUser.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }


}
