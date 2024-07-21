package uz.project.olix.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.project.olix.service.PhotoService;

@RestController
@RequestMapping("/photo")

@RequiredArgsConstructor
public class PhotoController {
    private final PhotoService photoService;
    @GetMapping("/get/{photoPath}")
    public ResponseEntity<Resource> getPhotoByPath(@PathVariable String photoPath) {
        return photoService.getPhotoByPath(photoPath);
    }
}
