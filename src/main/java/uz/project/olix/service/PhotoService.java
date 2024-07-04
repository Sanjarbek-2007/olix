package uz.project.olix.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.project.olix.dto.UploadPhotoDto;
import uz.project.olix.entity.Photo;
import uz.project.olix.exeptions.FileUploadFailedException;
import uz.project.olix.repositories.PhotoRepository;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final String[] paths = {"src/main/resources/images/profile","src/main/resources/images/documents", "src/main/resources/images/trucks"};
    private final PhotoRepository photoRepository;

    public void saveUserProfilePhoto(UploadPhotoDto photoDto, Long userId) throws FileUploadFailedException {
        for (MultipartFile file : photoDto.files()) {
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    String fileName = "Profile-" + userId + "-" + file.getOriginalFilename();
                    String filePath = paths[0] + fileName;


                    // Ensure the directory exists
                    File dir = new File(paths[0]);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                    stream.write(bytes);
                    stream.close();

                    Photo photo = photoRepository.save(Photo.builder().name(fileName).path(filePath).build());
                    photoRepository.addPhotoByPhotoIdAndUserId(photo.getId(), userId);

                } catch (IOException e) {
                    e.printStackTrace();
                    throw new FileUploadFailedException("File upload failed for file: " + file.getOriginalFilename());
                }
            }
        }
    }

    public ResponseEntity<Resource> getPhotoByPath(String photoPath) {
        Path imagePath = Paths.get(photoPath);
        ByteArrayResource resource;
        try {
            resource = new ByteArrayResource(Files.readAllBytes(imagePath));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // Set appropriate content type based on file extension
        MediaType mediaType = getImageMediaType(imagePath);

        // Prepare response with image data and content type
        return ResponseEntity.ok()
                .contentType(mediaType)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + photoPath + "\"")
                .body(resource);
    }
    private MediaType getImageMediaType (Path imagePath){
        String fileName = imagePath.getFileName().toString();
        String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

        switch (fileExtension) {
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "png":
                return MediaType.IMAGE_PNG;
            case "gif":
                return MediaType.IMAGE_GIF;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

}



