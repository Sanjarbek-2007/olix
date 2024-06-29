package uz.project.olix.dto;

public record SignupDto(
        String phoneNumber,
        String username,
        String fullName,
        String password
) {}
