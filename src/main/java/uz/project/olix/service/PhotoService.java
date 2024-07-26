package uz.project.olix.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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

    private final String[] paths = {"src/main/resources/images/profile/","src/main/resources/images/documents/", "src/main/resources/images/trucks/","src/main/resources/images/cargos/"};
    private final PhotoRepository photoRepository;

    public List<Photo> saveUserProfilePhoto(UploadPhotoDto photoDto, Long userId) throws FileUploadFailedException {
        List<Photo> photos = new ArrayList<>();
        for (MultipartFile file : photoDto.files()) {
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    String fileName = "Profile-" + userId + "-" + file.getOriginalFilename();
                    String filePath = paths[0] + fileName;


                    File dir = new File(paths[0]);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                    stream.write(bytes);
                    stream.close();

                    Photo photo = photoRepository.save(Photo.builder().name(fileName).path(filePath).build());
                    photoRepository.addPhotoByPhotoIdAndUserId(photo.getId(), userId);
                    photos.add(photo);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new FileUploadFailedException("File upload failed for file: " + file.getOriginalFilename());
                }
            }
        }
        return photos;
    }
    public List<Photo> saveTruckPhoto(List<MultipartFile> photoDto, Long truckId) throws FileUploadFailedException {
        List<Photo> photos = new ArrayList<>();
        for (MultipartFile file : photoDto) {
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    String fileName = "Truck-" + truckId + "-" + file.getOriginalFilename();
                    String filePath = paths[2] + fileName;


                    // Ensure the directory exists
                    File dir = new File(paths[2]);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                    stream.write(bytes);
                    stream.close();

                    Photo photo = photoRepository.save(Photo.builder().name(fileName).path(filePath).build());
                    photoRepository.addPhotoByPhotoIdAndTruckId(photo.getId(), truckId);
                    photos.add(photo);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new FileUploadFailedException("File upload failed for file: " + file.getOriginalFilename());
                }
            }
        }
        return photos;
    }


    public List<Photo> saveCargoPhotos(List<MultipartFile> photoDto, Long cargoId) throws FileUploadFailedException {
        List<Photo> photos = new ArrayList<>();
        for (MultipartFile file : photoDto) {
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    String fileName = "Cargo-" + cargoId + "-" + file.getOriginalFilename();
                    String filePath = paths[3] + File.separator + fileName;

                    File dir = new File(paths[3]);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                    stream.write(bytes);
                    stream.close();

                    Photo photo = photoRepository.save(Photo.builder().name(fileName).path(filePath).build());
                    photoRepository.addPhotoByPhotoIdAndCargoId(photo.getId(), cargoId);
                    photos.add(photo);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new FileUploadFailedException("File upload failed for file: " + file.getOriginalFilename());
                }
            }
        }
        return photos;
    }


    public ResponseEntity<Resource> getPhotoByPath(String photoPath) {
        Path imagePath = Paths.get(photoPath);
        ByteArrayResource resource;
        try {
            resource = new ByteArrayResource(Files.readAllBytes(imagePath));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        MediaType mediaType = getImageMediaType(imagePath);

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

    public List<Photo> addDocumentPhotos(List<MultipartFile> multipartFiles, Long id) throws FileUploadFailedException {
        List<Photo> photos = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    String fileName = "Document-" + id + "-" + file.getOriginalFilename();
                    String filePath = paths[0] + fileName;


                    // Ensure the directory exists
                    File dir = new File(paths[0]);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                    stream.write(bytes);
                    stream.close();

                    Photo photo = Photo.builder().name(fileName).path(filePath).build();
                    photos.add(photo);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new FileUploadFailedException("File upload failed for file: " + file.getOriginalFilename());
                }
            }
        }
        return photos;
    }
}



