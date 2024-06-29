package uz.project.olix.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uz.project.olix.entity.Role;
import uz.project.olix.entity.User;
import uz.project.olix.repositories.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String phoneNum) throws UsernameNotFoundException {
        User user = userRepository.findByPhoneNumber(phoneNum/*).or(
                () -> userRepository.findByPhoneNumber(phoneNum)*/
        ).orElseThrow(

                () -> new UsernameNotFoundException("User not found: "+ phoneNum));
        return new org.springframework.security.core.userdetails.User(phoneNum, user.getPassword(), mapRolesToAuthorities(user.getRoles()) );
    }

    public Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return roles.stream().map(role ->
                new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());
    }
}
