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
import uz.project.olix.dto.BecomeDriverDto;
import uz.project.olix.entity.User;
import uz.project.olix.service.ProfileService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;

////    @PostMapping("/becomeDriver")
//    public ResponseEntity<?> becomeDriver(@RequestBody BecomeDriverDto dto) {
//        return profileService.becomeDriver(dto);
//    }

    @GetMapping("/get")
    public ResponseEntity<User> getProfile() {
        return profileService.getProfile();
    }

    @PostMapping("/editFullname")
    public ResponseEntity<User> editFullname(@RequestParam("fullname") String fullname) {
        return profileService.editFullname(fullname);
    }

    @PostMapping("/editPassword")
    public ResponseEntity<String> editPassword(@RequestParam("password") String password) {
        return profileService.editPassword(password);
    }



}
