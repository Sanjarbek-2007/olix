package uz.project.olix.dto;

public record SignupDto(
        String phoneNumber,
        String email,
        String fullName,
        String password
) {}
