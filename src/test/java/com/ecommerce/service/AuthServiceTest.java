package com.ecommerce.service;

import com.ecommerce.domain.Role;
import com.ecommerce.domain.User;
import com.ecommerce.dto.auth.AuthResponse;
import com.ecommerce.dto.auth.LoginRequest;
import com.ecommerce.dto.auth.MeResponse;
import com.ecommerce.dto.auth.RegisterRequest;
import com.ecommerce.exception.ConflictException;
import com.ecommerce.exception.UnauthorizedException;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(userRepository, passwordEncoder, jwtService);
    }

    @Test
    void registerCreatesUser() {
        when(userRepository.existsByEmailIgnoreCase("user@shop.local")).thenReturn(false);
        when(passwordEncoder.encode("Parola123")).thenReturn("hash");
        when(jwtService.generateToken(any(), any(), any())).thenReturn("token");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        AuthResponse response = authService.register(new RegisterRequest("  User@shop.local ", "Parola123"));

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertEquals("user@shop.local", captor.getValue().getEmail());
        assertEquals(Role.USER, captor.getValue().getRole());
        assertEquals("token", response.accessToken());
        assertEquals("user@shop.local", response.email());
    }

    @Test
    void registerRejectsDuplicateEmail() {
        when(userRepository.existsByEmailIgnoreCase("user@shop.local")).thenReturn(true);

        assertThrows(ConflictException.class, () ->
                authService.register(new RegisterRequest("user@shop.local", "Parola123")));
    }

    @Test
    void loginReturnsToken() {
        User user = user(2L, "user@shop.local", "hash", Role.USER);
        when(userRepository.findByEmailIgnoreCase("user@shop.local")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("Parola123", "hash")).thenReturn(true);
        when(jwtService.generateToken(2L, "user@shop.local", Role.USER)).thenReturn("token");

        AuthResponse response = authService.login(new LoginRequest("user@shop.local", "Parola123"));

        assertEquals("token", response.accessToken());
        assertEquals(2L, response.id());
    }

    @Test
    void loginRejectsWrongPassword() {
        User user = user(2L, "user@shop.local", "hash", Role.USER);
        when(userRepository.findByEmailIgnoreCase("user@shop.local")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("gresit", "hash")).thenReturn(false);

        assertThrows(BadCredentialsException.class, () ->
                authService.login(new LoginRequest("user@shop.local", "gresit")));
    }

    @Test
    void meReturnsTypedDto() {
        User user = user(3L, "admin@shop.local", "hash", Role.ADMIN);
        when(userRepository.findByEmailIgnoreCase("admin@shop.local")).thenReturn(Optional.of(user));

        MeResponse response = authService.me("admin@shop.local");

        assertEquals("admin@shop.local", response.email());
        assertEquals("ADMIN", response.role());
    }

    @Test
    void meRejectsUnknownEmail() {
        when(userRepository.findByEmailIgnoreCase("nimeni@shop.local")).thenReturn(Optional.empty());

        assertThrows(UnauthorizedException.class, () -> authService.me("nimeni@shop.local"));
    }

    private User user(Long id, String email, String passwordHash, Role role) {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setPasswordHash(passwordHash);
        user.setRole(role);
        return user;
    }
}
