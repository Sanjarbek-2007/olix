package uz.project.olix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.project.olix.entity.Cargo;
import uz.project.olix.entity.Truck;

import java.util.List;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
    List<Cargo> findByName(String name);
    List<Cargo> findByWeightBetween(double minWeight, double maxWeight);
    List<Cargo> findByTruck(Truck truck);
    List<Cargo> findByOwnerId(Long ownerId);
    List<Cargo> findByStatus(String status);
}
