package uz.project.olix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.project.olix.entity.Truck;

public interface TruckRepository extends JpaRepository<Truck, Long> {
}
