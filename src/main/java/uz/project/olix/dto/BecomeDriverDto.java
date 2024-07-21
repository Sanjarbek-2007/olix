package uz.project.olix.dto;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record BecomeDriverDto(
        List<MultipartFile> driverLicence,
        List<MultipartFile> photos,
        String carNum,
        String model,
        String body


) {}
