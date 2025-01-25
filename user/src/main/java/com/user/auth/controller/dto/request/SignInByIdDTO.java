package com.user.auth.controller.dto.request;

public record SignInByIdDTO(
        String id,
        String password
) {
}
