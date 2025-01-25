package com.user.user.dto;

import com.user.user.repository.entity.Role;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;

public record UserDTO(String id, String name, String password, String email, String phone, String profile, Role role, String bio, String lang, Timestamp createdAt, Boolean emailRequest, Boolean phoneRequest, Long followers, Long following) {
}
