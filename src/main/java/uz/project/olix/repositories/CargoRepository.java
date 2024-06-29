package uz.project.olix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.project.olix.entity.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
}
