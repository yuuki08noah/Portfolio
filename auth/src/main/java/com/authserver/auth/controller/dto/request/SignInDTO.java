package com.authserver.auth.controller.dto.request;

public record SignInDTO(
        Long userId,
        String password
) {
}
