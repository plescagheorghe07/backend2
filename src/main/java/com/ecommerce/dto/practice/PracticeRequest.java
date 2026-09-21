package com.ecommerce.dto.practice;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PracticeRequest(
        @NotBlank @Size(max = 255) String message
) {
}
