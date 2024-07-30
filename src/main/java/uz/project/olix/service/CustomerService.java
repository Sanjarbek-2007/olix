package uz.project.olix.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.project.olix.dto.BecomeCustomerDto;
import uz.project.olix.entity.Document;
import uz.project.olix.entity.Role;
import uz.project.olix.entity.User;
import uz.project.olix.repositories.RoleRepository;
import uz.project.olix.repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final DocumentService documentService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public ResponseEntity<String> becomeCustomer(BecomeCustomerDto becomeCustomerDto) {
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Attempting to find user by phone number: {}", phoneNumber);
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> {
                    logger.error("User not found with phone number: {}", phoneNumber);
                    return new RuntimeException("User not found");
                });

        logger.info("Creating document for user: {}", user.getId());
        Document document = Document.builder()
                .documentSerial(becomeCustomerDto.getDocumentSerial())
                .documentType(becomeCustomerDto.getDocumentType())
                .owner(user).build();

        List<MultipartFile> documentPhotos = becomeCustomerDto.getDocumentPhotos();
        documentService.addDocument(document, documentPhotos);

        logger.info("Retrieving role 'CUSTOMER'");
        Role customerRole = roleRepository.findByName("CUSTOMER");
        if (customerRole == null) {
            logger.error("Role 'CUSTOMER' not found in the database");
            return new ResponseEntity<>("Role 'CUSTOMER' not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("Assigning role 'CUSTOMER' to user: {}", user.getId());
        userRepository.setRolesToUsersById(user.getId(), customerRole.getId());

        return new ResponseEntity<>("Successfully applied for being customer", HttpStatus.OK);
    }
}
