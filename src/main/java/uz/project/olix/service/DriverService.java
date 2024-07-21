package uz.project.olix.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.project.olix.dto.BecomeDriverDto;
import uz.project.olix.entity.Document;
import uz.project.olix.entity.Role;
import uz.project.olix.entity.User;
import uz.project.olix.repositories.RoleRepository;
import uz.project.olix.repositories.TruckRepository;
import uz.project.olix.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class DriverService {
    private final DocumentService documentService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TruckRepository truckRepository;
    private final TruckService truckService;

    public ResponseEntity<?> become(BecomeDriverDto dto) {
        Document drivingLicence = documentService.addDocument(new Document(dto.drivingLicenceSerialNumber(), "Driving Licence"), dto.driverLicence());
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
