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
            @ModelAttribute BecomeCustomerDto dto) {
        return customerService.becomeCustomer(dto);

    }
}
