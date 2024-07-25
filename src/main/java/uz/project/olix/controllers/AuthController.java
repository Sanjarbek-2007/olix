package uz.project.olix.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.project.olix.config.security.JwtResponse;
import uz.project.olix.dto.CheckUserExistaceDto;
import uz.project.olix.dto.LoginDto;
import uz.project.olix.dto.SignupDto;
import uz.project.olix.exeptions.UserAlreadyExistExeption;
import uz.project.olix.responces.CheckUserExistanceResponse;
import uz.project.olix.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<JwtResponse> signup(@RequestBody SignupDto dto) throws UserAlreadyExistExeption {
        log.info(dto.phoneNumber());
        return authService.signup(dto);
    }
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }
    @PostMapping("/check")
    public ResponseEntity<CheckUserExistanceResponse> check(@RequestBody CheckUserExistaceDto dto){
        return new ResponseEntity<>(authService.checkExistance(dto), HttpStatus.OK);
    }

}
