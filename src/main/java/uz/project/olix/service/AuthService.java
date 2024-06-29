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
import uz.project.olix.dto.LoginDto;
import uz.project.olix.dto.SignupDto;
import uz.project.olix.entity.User;
import uz.project.olix.exeptions.UserAlreadyExistExeption;
import uz.project.olix.repositories.RoleRepository;
import uz.project.olix.repositories.UserRepository;

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
                .username(dto.username())
                .fullName(dto.fullName())
                .isConfirmed(false)
                .phoneNumber(dto.phoneNumber())
                .build();
    }

    public ResponseEntity<JwtResponse> signup(SignupDto dto) throws UserAlreadyExistExeption {
        if (userRepository.existsByPhoneNumber(dto.phoneNumber())) {
//            return new ResponseEntity<>(HttpStatus.CONFLICT);
            throw new UserAlreadyExistExeption(dto.phoneNumber());
        }
        User user = getUserEntity(dto);

        if (user != null) {
            user.setRoles(Collections.singletonList(roleRepository.findByName("USER")));
            userRepository.save(user);
            String token = jwtProvider.generate(user);
            return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
        }
        return null;
    }
    public JwtResponse login(LoginDto loginDto){
        Optional<User> user = userRepository.findByPhoneNumber(loginDto.phoneNumber());
        if (user.isPresent() && passwordEncoder.matches(loginDto.password(), user.get().getPassword())){
            String token = jwtProvider.generate(user.orElse(null));
            return new JwtResponse(token);
        }
        return null;
    }
}
