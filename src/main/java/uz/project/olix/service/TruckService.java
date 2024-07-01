package uz.project.olix.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import uz.project.olix.entity.Truck;
import uz.project.olix.repositories.TruckRepository;

@Service
@RequiredArgsConstructor
public class TruckService {

    private final TruckRepository truckRepository;

    public List<Truck> getAllTrucks() {
        return truckRepository.findAll();
    }

    public Optional<Truck> getTruckById(Long id) {
        return truckRepository.findById(id);
    }

    public Truck saveTruck(Truck truck) {
        return truckRepository.save(truck);
    }

    public Optional<Truck> updateTruck(Long id, Truck truckDetails) {
        return truckRepository.findById(id).map(truck -> {
            truck.setName(truckDetails.getName());
            truck.setOwner(truckDetails.getOwner());
            truck.setTechnicalPassport(truckDetails.getTechnicalPassport());
            truck.setCargos(truckDetails.getCargos());
            return truckRepository.save(truck);
        });
    }

    public boolean deleteTruck(Long id) {
        return truckRepository.findById(id).map(truck -> {
            truckRepository.delete(truck);
            return true;
        }).orElse(false);
    }
}
