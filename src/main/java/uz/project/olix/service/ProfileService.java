package uz.project.olix.service;

import jakarta.transaction.Transactional;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.project.olix.dto.BecomeDriverDto;
import uz.project.olix.entity.Role;
import uz.project.olix.entity.Truck;
import uz.project.olix.entity.User;
import uz.project.olix.repositories.PhotoRepository;
import uz.project.olix.repositories.RoleRepository;
import uz.project.olix.repositories.TruckRepository;
import uz.project.olix.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;
    private final PasswordEncoder passwordEncoder;
    private final TruckRepository truckRepository;
    private final RoleRepository roleRepository;
    private final TruckService truckService;


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
    public ResponseEntity<String> becomeDriver(BecomeDriverDto dto) {
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!phoneNumber.isBlank()) {
            User user = userRepository.findByPhoneNumber(phoneNumber).get();
            boolean existance = false;
            for (Role role : user.getRoles()) {if (role.getName().equals("DRIVER")) {existance = true;break;}}

            if (!existance) {
                if (truckService.addTruck(dto,user)) {
                    Role adminRole = roleRepository.findByName("DRIVER");
                    userRepository.setRolesToUsersById(adminRole.getId(), adminRole.getId());
                    return new ResponseEntity<>("Driver added successfully", HttpStatus.OK);
                }
                return new ResponseEntity<>("Problem in adding data", HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>("Driver already exists", HttpStatus.OK);


        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
