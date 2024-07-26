package uz.project.olix.service;

import lombok.RequiredArgsConstructor;
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
    private final DocumentService documentService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public ResponseEntity<String> becomeCustomer(BecomeCustomerDto becomeCustomerDto) {
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Document document = Document.builder()
                .documentSerial(
                        becomeCustomerDto.getDocumentSerial())
                .documentType(becomeCustomerDto.getDocumentType())
                .owner(user).build();

        List<MultipartFile> documentPhotos = becomeCustomerDto.getDocumentPhotos();
        documentService.addDocument(document, documentPhotos);
        Role customer = roleRepository.findByName("CUSTOMER");
        userRepository.setRolesToUsersById(customer.getId(), user.getId());
        return new ResponseEntity<>("Succesfully applied for being customer", HttpStatus.OK);
    }
}
