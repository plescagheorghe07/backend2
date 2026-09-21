package com.ecommerce.dto.practice;

import java.time.Instant;

public record PracticeResponse(Long id, String message, Instant updatedAt) {
}
