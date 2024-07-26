package uz.project.olix.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.project.olix.service.PhotoService;

@RestController
@RequestMapping("/photo")
@RequiredArgsConstructor
@Slf4j
public class PhotoController {
    private final PhotoService photoService;
    @GetMapping("/get")
    public ResponseEntity<Resource> getPhotoByPath(@RequestParam String photoPath) {
        log.info(photoPath + " get photo used");
        return photoService.getPhotoByPath(photoPath);
    }
}
