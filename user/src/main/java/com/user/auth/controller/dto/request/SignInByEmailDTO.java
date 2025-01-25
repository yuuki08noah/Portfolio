package com.user.auth.controller.dto.request;

public record SignInByEmailDTO (
        String email,
        String password
) {
}
