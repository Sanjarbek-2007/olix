package uz.project.olix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.project.olix.entity.Trip;

import java.util.List;
@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByTripStatus_Status(String status);

}
