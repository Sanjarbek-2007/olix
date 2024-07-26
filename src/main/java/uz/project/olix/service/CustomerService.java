package uz.project.olix.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.project.olix.dto.BecomeCustomerDto;
import uz.project.olix.entity.Document;
import uz.project.olix.entity.User;
import uz.project.olix.repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final DocumentService documentService;
    private final UserRepository userRepository;

    public void becomeCustomer(Long userId, BecomeCustomerDto becomeCustomerDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Document document = new Document();
        document.setDocumentSerial(becomeCustomerDto.getDocumentSerial());
        document.setDocumentType(becomeCustomerDto.getDocumentType());
        document.setOwner(user);

        List<MultipartFile> documentPhotos = becomeCustomerDto.getDocumentPhotos();
        documentService.addDocument(document, documentPhotos);
    }
}
