package uz.project.olix.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.project.olix.dto.AddingSuccess;
import uz.project.olix.dto.BecomeDriverDto;
import uz.project.olix.entity.Document;
import uz.project.olix.entity.Role;
import uz.project.olix.entity.User;
import uz.project.olix.repositories.RoleRepository;
import uz.project.olix.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverService {
    private static final Logger logger = LoggerFactory.getLogger(DriverService.class);

    private final DocumentService documentService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TruckService truckService;
    private final AuthService authService;

    public ResponseEntity<?> become(BecomeDriverDto dto) {
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!phoneNumber.isBlank()) {
            User user = userRepository.findByPhoneNumber(phoneNumber).orElse(null);
            if (user == null) {
                logger.error("User not found for phone number: {}", phoneNumber);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Document drivingLicence = documentService.addDocument(new Document(dto.drivingLicenceSerialNumber(), "Driving Licence", user), dto.driverLicence());
            boolean exists = user.getRoles().stream().anyMatch(role -> role.getName().equals("DRIVER"));

            if (!exists) {
                logger.info("Adding truck for user: {}", user.getPhoneNumber());
                if (truckService.addTruck(dto, user)) {
                    logger.info("Fetching DRIVER role from the database");
                    Role driverRole = roleRepository.findByName("DRIVER");
                    if (driverRole == null) {
                        logger.error("Role DRIVER not found");
                        return new ResponseEntity<>("Role DRIVER not found", HttpStatus.INTERNAL_SERVER_ERROR);
                    }

                    List<Role> roles = new ArrayList<>(user.getRoles());
                    roles.add(driverRole);
                    user.setRoles(roles);
                    userRepository.save(user);

                    logger.info("Role DRIVER added to user: {}", user.getPhoneNumber());
                    return new ResponseEntity<>(
                            new AddingSuccess("Successfully added ",authService.refreshToken(user))
                            , HttpStatus.OK);
                }
                logger.error("Problem in adding truck data for user: {}", user.getPhoneNumber());
                return new ResponseEntity<>("Problem in adding data", HttpStatus.CONFLICT);
            }
            logger.info("User: {} already has DRIVER role", user.getPhoneNumber());
            return new ResponseEntity<>("Driver already exists", HttpStatus.OK);
        }
        logger.error("Unauthorized attempt to become driver");
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
