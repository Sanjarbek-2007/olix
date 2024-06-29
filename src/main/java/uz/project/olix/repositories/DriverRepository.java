package uz.project.olix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.project.olix.entity.Driver;

public interface DriverRepository extends JpaRepository<Driver, Long> {
}
