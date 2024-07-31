package uz.project.olix.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.project.olix.dto.AddCargoDto;
import uz.project.olix.entity.Cargo;
import uz.project.olix.entity.Photo;
import uz.project.olix.entity.User;
import uz.project.olix.exeptions.FileUploadFailedException;
import uz.project.olix.repositories.CargoRepository;
import uz.project.olix.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class CargoService {

    private final CargoRepository cargoRepository;
    private final PhotoService photoService;
    private final UserRepository userRepository;

    public List<Cargo> getAllCargos() {
        return cargoRepository.findAll();
    }

    public Optional<Cargo> getCargoById(Long id) {
        return cargoRepository.findById(id);
    }

    public Cargo saveCargo(AddCargoDto cargo) {
        // Retrieve the phone number of the authenticated user
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();

        // Fetch the user entity using the phone number
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create a new Cargo entity using the provided data
//        Cargo cargoEntity = new Cargo(cargo.name(), cargo.weight(), cargo.status());
Cargo cargoEntity = new Cargo(cargo.name(),cargo.status() , user, cargo.weight());

        // Save the Cargo entity to the repository
        Cargo savedCargo = cargoRepository.save(cargoEntity);

        // Update the owner of the saved cargo to be the authenticated user
//        cargoRepository.updateOwnerById(user.getId(), savedCargo.getId());

        // Save the photos associated with the cargo
        try {
            photoService.saveCargoPhotos(cargo.photos(), savedCargo.getId());
        } catch (FileUploadFailedException e) {
            // Handle the exception as needed
            throw new RuntimeException("Failed to upload photos", e);
        }

        return savedCargo;
    }

    public Optional<Cargo> updateCargo(Long id, Cargo cargoDetails) {
        return cargoRepository.findById(id).map(cargo -> {
            cargo.setName(cargoDetails.getName());
            cargo.setWeight(cargoDetails.getWeight());
            return cargoRepository.save(cargo);
        });
    }

    public boolean deleteCargo(Long id) {
        return cargoRepository.findById(id).map(cargo -> {

            photoService.deletePhotos(cargo.getPhotos(),cargo);
            cargoRepository.delete(cargo);
            return true;
        }).orElse(false);

    }

    public List<Cargo> getCargoByName(String name) {
        return cargoRepository.findByName(name);
    }

    public List<Cargo> getCargoByWeight(double minWeight, double maxWeight) {
        return cargoRepository.findByWeightBetween(minWeight, maxWeight);
    }





    public List<Cargo> getCargoByUser(Long userId) {
        return cargoRepository.findByOwnerId(userId);
    }

    public boolean deleteMultipleCargos(List<Long> ids) {
        return cargoRepository.findAllById(ids).stream()
                .peek(cargo -> cargoRepository.delete(cargo))
                .count() == ids.size();
    }

    public boolean updateCargoPhotos(Long cargoId, List<Photo> photos) {
        return cargoRepository.findById(cargoId)
                .map(cargo -> {
                    cargo.setPhotos(photos);
                    cargoRepository.save(cargo);
                    return true;
                }).orElse(false);
    }

    public List<Cargo> getCargoByStatus(String status) {
        return cargoRepository.findByStatus(status);
    }

    public List<Cargo> getAllCargosByOwner() {
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        return cargoRepository.findByOwner_PhoneNumber(phoneNumber);
    }
}
