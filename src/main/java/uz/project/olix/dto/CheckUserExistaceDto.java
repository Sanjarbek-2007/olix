package uz.project.olix.dto;

import lombok.Builder;

@Builder
public record CheckUserExistaceDto(
        String phoneNumber,
        String email
) {}
