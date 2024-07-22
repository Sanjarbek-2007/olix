package uz.project.olix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.project.olix.entity.Trip;

public interface TripRepository extends JpaRepository<Trip, Long> {
}
