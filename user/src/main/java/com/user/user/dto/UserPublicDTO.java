package com.user.user.dto;

import com.user.user.repository.entity.Role;

import java.sql.Timestamp;

public record UserPublicDTO(String id, String name, String email, String phone, String profile, Role role, String bio, String lang, Timestamp createdAt, Boolean emailRequest, Boolean phoneRequest, Long followers, Long following) {
}
