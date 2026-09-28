package com.ecommerce.security;

import com.ecommerce.domain.Role;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService("cheie-secreta-de-test-cu-minim-32-caractere!", 3_600_000L);
    }

    @Test
    void generateAndParseToken() {
        String token = jwtService.generateToken(7L, "admin@shop.local", Role.ADMIN);
        Claims claims = jwtService.parse(token);

        assertEquals("7", claims.getSubject());
        assertEquals("admin@shop.local", claims.get("email", String.class));
        assertEquals("ADMIN", claims.get("role", String.class));
        assertNotNull(claims.getExpiration());
        assertEquals(7L, jwtService.extractUserId(token));
    }

    @Test
    void rejectInvalidToken() {
        assertThrows(Exception.class, () -> jwtService.parse("token.inval.id"));
    }
}
