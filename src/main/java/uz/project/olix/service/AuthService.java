package uz.project.olix.service;

import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.project.olix.config.security.JwtProvider;
import uz.project.olix.config.security.JwtResponse;
import uz.project.olix.dto.CheckUserExistaceDto;
import uz.project.olix.dto.LoginDto;
import uz.project.olix.dto.SignupDto;
import uz.project.olix.entity.User;
import uz.project.olix.exeptions.UserAlreadyExistExeption;
import uz.project.olix.repositories.RoleRepository;
import uz.project.olix.repositories.UserRepository;
import uz.project.olix.responces.CheckUserExistanceResponse;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public User getUserEntity(SignupDto dto){
        System.out.println(dto.phoneNumber());
        return User.builder()
                .password(passwordEncoder.encode(dto.password()))
                .email(dto.email())
                .fullName(dto.fullName())
                .isConfirmed(false)
                .phoneNumber(dto.phoneNumber())
                .build();
    }

    public ResponseEntity<JwtResponse> signup(SignupDto dto) throws UserAlreadyExistExeption {
        if (!checkExistance(new CheckUserExistaceDto(dto.phoneNumber(), dto.email())).exists()) {
            User user = getUserEntity(dto);
            if (user != null) {
            user.setRoles(Collections.singletonList(roleRepository.findByName("USER")));
            userRepository.save(user);
            String token = jwtProvider.generate(user);
            return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
        }
        }
        return new ResponseEntity<>(null,HttpStatus.CONFLICT);
    }
    public JwtResponse login(LoginDto loginDto){
        Optional<User> user = userRepository.findByPhoneNumber(loginDto.phoneNumber());
        if (user.isPresent() && passwordEncoder.matches(loginDto.password(), user.get().getPassword())){
            String token = jwtProvider.generate(user.orElse(null));
            return new JwtResponse(token);
        }
        return null;
    }
    public JwtResponse refreshToken(User user){
            String token = jwtProvider.generate(user );
            return new JwtResponse(token);
    }
    public CheckUserExistanceResponse checkExistance(CheckUserExistaceDto dto) {
        if(dto.phoneNumber().isEmpty() && dto.email().isEmpty()){
            return new CheckUserExistanceResponse(null, "Please enter a valid phone number or email");
        }
        if (!dto.phoneNumber().isEmpty()){
            String phoneNumber = dto.phoneNumber();
            return  new CheckUserExistanceResponse(userRepository.existsByPhoneNumber(phoneNumber),phoneNumber);

        }
        if (!dto.email().isEmpty()) {
            String email = dto.email();
            return  new CheckUserExistanceResponse(userRepository.existsByEmail(email),email);
        }
        return new CheckUserExistanceResponse(false, "Phone number or email is free");
    }
}
