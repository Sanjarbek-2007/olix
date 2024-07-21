package uz.project.olix.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.project.olix.dto.BecomeDriverDto;
import uz.project.olix.service.DriverService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/drivers")
public class DriverController {
    private final DriverService driverService;

    @PostMapping("/become")
    public ResponseEntity<?> becomeDriver(@ModelAttribute BecomeDriverDto dto){
        return new ResponseEntity<>(driverService.become(dto), HttpStatus.OK);
    }
}
