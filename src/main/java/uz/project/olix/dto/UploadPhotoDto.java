package uz.project.olix.dto;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record UploadPhotoDto(
        List<MultipartFile> files
) {}
