package uz.project.olix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.project.olix.entity.User;
import uz.project.olix.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByPhoneNumber(String phoneNumber) {
        String trimmedPhoneNumber = phoneNumber.trim();
        System.out.println("Searching for phone number: " + trimmedPhoneNumber);
        Optional<User> user = userRepository.findByPhoneNumber(trimmedPhoneNumber);
        System.out.println("Found user: " + user);
        return user;
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

}