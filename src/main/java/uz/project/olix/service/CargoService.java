package uz.project.olix.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.project.olix.entity.Cargo;

import java.util.List;
import java.util.Optional;
import uz.project.olix.repositories.CargoRepository;

@Service
@RequiredArgsConstructor
public class CargoService {

    private final CargoRepository cargoRepository;

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
}
