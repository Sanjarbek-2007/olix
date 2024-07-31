package uz.project.olix.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.project.olix.dto.UserDto;
import uz.project.olix.entity.Truck;
import uz.project.olix.entity.User;
import uz.project.olix.repositories.CargoRepository;
import uz.project.olix.repositories.DocumentRepository;
import uz.project.olix.repositories.TruckRepository;
import uz.project.olix.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private final DocumentRepository documentRepository;

    private final TruckRepository truckRepository;

    private final CargoRepository cargoRepository;



    //
//    public Optional<User> findByPhoneNumber(String phoneNumber) {
//        String trimmedPhoneNumber = phoneNumber.trim();
//        System.out.println("Searching for phone number: " + trimmedPhoneNumber);
//        Optional<User> user = userRepository.findByPhoneNumber(trimmedPhoneNumber);
//        System.out.println("Found user: " + user);
//        return user;
//    }
//
//    public Optional<User> findById(Long id) {
//        return userRepository.findById(id);
//    }



    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id).map(this::convertToDTO);
    }

    public Optional<UserDto> findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber.trim()).map(this::convertToDTO);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public UserDto updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setFullName(userDetails.getFullName());
        user.setPassword(userDetails.getPassword());
        user.setProfilePicture(userDetails.getProfilePicture());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setProfilePhotos(userDetails.getProfilePhotos());
        user.setIsConfirmed(userDetails.getIsConfirmed());
        user.setDocuments(userDetails.getDocuments());
        return convertToDTO(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(Long id) {
        // Fetch the user and check if exists
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));

        // Delete all cargo associated with trucks related to the user
        System.out.println("Deleting cargo for trucks of user with ID: " + id);
        List<Truck> trucks = truckRepository.findByOwnerId(id);
        for (Truck truck : trucks) {
            cargoRepository.deleteByTruckId(truck.getId());
        }

        // Delete all trucks associated with the user
        System.out.println("Deleting trucks for user with ID: " + id);
        truckRepository.deleteByOwnerId(id);

        // Delete all documents associated with the user
        System.out.println("Deleting documents for user with ID: " + id);
        documentRepository.deleteByOwnerId(id);

        // Delete the user
        System.out.println("Deleting user with ID: " + id);
        userRepository.delete(user);

        System.out.println("User deleted successfully.");
    }

    private UserDto convertToDTO(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .profilePicture(user.getProfilePicture())
                .phoneNumber(user.getPhoneNumber())
                .profilePhotos(user.getProfilePhotos())
                .isConfirmed(user.getIsConfirmed())
                .documents(user.getDocuments())
                .build();
    }

}
