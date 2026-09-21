package com.ecommerce.dto.practice;

import jakarta.validation.constraints.Size;

public record PatchPracticeRequest(
        @Size(max = 255) String message
) {
}
