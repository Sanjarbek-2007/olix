package uz.project.olix.service;

import jakarta.transaction.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.project.olix.dto.BecomeDriverDto;
import uz.project.olix.dto.UploadPhotoDto;
import uz.project.olix.entity.Role;
import uz.project.olix.entity.Truck;
import uz.project.olix.entity.User;
import uz.project.olix.exeptions.FileUploadFailedException;
import uz.project.olix.repositories.PhotoRepository;
import uz.project.olix.repositories.RoleRepository;
import uz.project.olix.repositories.TruckRepository;
import uz.project.olix.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final TruckService truckService;
    private final PhotoService photoService;


//    private final String uploadDir = "src/main/resources/profile";

    public ResponseEntity<User> getProfile() {
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!phoneNumber.isBlank()) {
            User user = userRepository.findByPhoneNumber(phoneNumber).orElse(null);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Transactional
    public ResponseEntity<User> editFullname(String fullname) {
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!phoneNumber.isBlank()) {
            userRepository.updateFullNameByPhoneNumber(phoneNumber, fullname);
            User updatedUser = userRepository.findByPhoneNumber(phoneNumber).orElse(null);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Transactional
    public ResponseEntity<String> editPassword(String password) {
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!phoneNumber.isBlank()) {
            String encodedPassword = passwordEncoder.encode(password);
            userRepository.updatePasswordByPhoneNumber(phoneNumber, encodedPassword);
            return new ResponseEntity<>("Password edited successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Transactional
    public ResponseEntity<User> editEmail(String email) {
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!phoneNumber.isBlank()) {
            userRepository.updateEmailByPhoneNumber(phoneNumber, email);
            User updatedUser = userRepository.findByPhoneNumber(phoneNumber).orElse(null);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Transactional
    public ResponseEntity<User> editPhoneNumber(String phoneNumber) {
        String currentPhoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!currentPhoneNumber.isBlank()) {
            userRepository.updatePhoneNumberByPhoneNumber(currentPhoneNumber, phoneNumber);
            User updatedUser = userRepository.findByPhoneNumber(phoneNumber).orElse(null);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Transactional
    public ResponseEntity<String> uploadProfilePicture(MultipartFile file) {
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!phoneNumber.isBlank()) {
            try {
                UploadPhotoDto photoDto = new UploadPhotoDto(List.of(file));
                Long userId = userRepository.findByPhoneNumber(phoneNumber).get().getId();
                photoService.saveUserProfilePhoto(photoDto, userId);

                String fileName = "Profile-" + userId + "-" + file.getOriginalFilename();
                userRepository.updateProfilePictureByPhoneNumber(phoneNumber, fileName);

                return new ResponseEntity<>("Profile picture uploaded successfully", HttpStatus.OK);
            } catch (FileUploadFailedException e) {
                return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


   




}
