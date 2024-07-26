package uz.project.olix.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.project.olix.dto.BecomeCustomerDto;
import uz.project.olix.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/become")
    public ResponseEntity<String> becomeCustomer(
            @RequestParam("userId") Long userId,
            @RequestParam("documentSerial") String documentSerial,
            @RequestParam("documentType") String documentType,
            @RequestParam("documentPhotos") List<MultipartFile> documentPhotos) {

        BecomeCustomerDto dto = new BecomeCustomerDto(documentSerial, documentType, documentPhotos);
        customerService.becomeCustomer(userId, dto);

        return ResponseEntity.ok("Customer registration successful");
    }
}
