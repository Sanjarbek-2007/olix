package uz.project.olix.dto;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record AddCargoDto(
        String name,
        float weight,
        String status,
        List<MultipartFile> photos

) {
}
