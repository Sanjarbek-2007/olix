package uz.project.olix.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.project.olix.dto.BecomeCargoDto;
import uz.project.olix.entity.Cargo;

import java.util.List;
import java.util.Optional;

import uz.project.olix.entity.Photo;
import uz.project.olix.entity.Truck;
import uz.project.olix.entity.User;
import uz.project.olix.exeptions.FileUploadFailedException;
import uz.project.olix.repositories.CargoRepository;
import uz.project.olix.repositories.PhotoRepository;

@Service
@RequiredArgsConstructor
public class CargoService {

    private final CargoRepository cargoRepository;
    private final PhotoService photoService;

    public List<Cargo> getAllCargos() {
        return cargoRepository.findAll();
    }

    public Optional<Cargo> getCargoById(Long id) {
        return cargoRepository.findById(id);
    }

    public Cargo saveCargo(Cargo cargo) {
        return cargoRepository.save(cargo);
    }

    public Optional<Cargo> updateCargo(Long id, Cargo cargoDetails) {
        return cargoRepository.findById(id).map(cargo -> {
            cargo.setName(cargoDetails.getName());
            cargo.setWeight(cargoDetails.getWeight());
            cargo.setTruck(cargoDetails.getTruck());
            return cargoRepository.save(cargo);
        });
    }

    public boolean deleteCargo(Long id) {
        return cargoRepository.findById(id).map(cargo -> {
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

    public List<Cargo> getCargoByTruck(Truck truck) {
        return cargoRepository.findByTruck(truck);
    }

    public boolean addCargo(BecomeCargoDto dto, User user, Truck truck) {
        if (dto.getName() != null && dto.getWeight() > 0) {
            Cargo cargo = new Cargo();
            cargo.setName(dto.getName());
            cargo.setWeight(dto.getWeight());
            cargo.setTruck(truck);
            cargo.setOwner(user);
            Cargo savedCargo = cargoRepository.save(cargo);

            try {
                List<Photo> photos = photoService.saveCargoPhotos(dto.getPhotos(), savedCargo.getId());
                if (!photos.isEmpty()) {
                    return true;
                }
                return false;
            } catch (FileUploadFailedException e) {
                throw new RuntimeException("Failed to save cargo photos: " + e.getMessage(), e);
            }
        }
        return false;
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

}
