package com.ecommerce.dto.auth;

import com.ecommerce.domain.Role;

public record AuthResponse(
        String accessToken,
        String tokenType,
        Long id,
        String email,
        Role role
) {
    public AuthResponse(String accessToken, Long id, String email, Role role) {
        this(accessToken, "Bearer", id, email, role);
    }
}
