package uz.project.olix.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uz.project.olix.dto.BecomeDriverDto;
import uz.project.olix.entity.Photo;
import uz.project.olix.entity.User;
import uz.project.olix.service.ProfileService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;


//        @PostMapping("/becomeDriver")
//        public ResponseEntity<String> becomeDriver(@RequestBody BecomeDriverDto dto) {
//            return profileService.becomeDriver(dto);
//        }

    @GetMapping("/get")
    public ResponseEntity<User> getProfile() {
        return profileService.getProfile();
    }

    @GetMapping("/profilePhoto")
    public ResponseEntity<Photo> getProfilePhoto() {
        return profileService.getProfilePhoto();
    }

    @PostMapping("/editFullname")
    public ResponseEntity<User> editFullname(@RequestParam("fullname") String fullname) {
        return profileService.editFullname(fullname);
    }

    @PostMapping("/editPassword")
    public ResponseEntity<String> editPassword(@RequestParam("password") String password) {
        return profileService.editPassword(password);
    }

    @PostMapping("/editEmail")
    public ResponseEntity<User> editEmail(@RequestParam("email") String email) {
        return profileService.editEmail(email);
    }

    @PostMapping("/editPhoneNumber")
    public ResponseEntity<User> editPhoneNumber(@RequestParam("phoneNumber") String phoneNumber) {
        return profileService.editPhoneNumber(phoneNumber);
    }

    @PostMapping("/uploadProfilePicture")
    public ResponseEntity<String> uploadProfilePicture(@RequestParam("file") MultipartFile file) {
        return profileService.uploadProfilePicture(file);
    }
}




