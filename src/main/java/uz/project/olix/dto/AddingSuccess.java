package uz.project.olix.dto;

import uz.project.olix.config.security.JwtResponse;

public record AddingSuccess(
        String status,
        JwtResponse jwtResponse
        ) {}
