package uz.project.olix.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(DriverService.class);
    private final DocumentService documentService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TruckRepository truckRepository;
    private final TruckService truckService;

    public ResponseEntity<?> become(BecomeDriverDto dto) {
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!phoneNumber.isBlank()) {
            User user = userRepository.findByPhoneNumber(phoneNumber).get();
            Document drivingLicence = documentService.addDocument(new Document(dto.drivingLicenceSerialNumber(), "Driving Licence",user ), dto.driverLicence());
            boolean existance = false;
            for (Role role : user.getRoles()) {if (role.getName().equals("DRIVER")) {existance = true;break;}}

            if (!existance) {
                if (truckService.addTruck(dto,user)) {
                    Role adminRole = roleRepository.findByName("DRIVER");
                    log.info(adminRole.getName()+" has been added to the user");
                    userRepository.setRolesToUsersById(adminRole.getId(),adminRole.getId());
                    return new ResponseEntity<>("Driver added successfully", HttpStatus.OK);
                }
                return new ResponseEntity<>("Problem in adding data", HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>("Driver already exists", HttpStatus.OK);


        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
