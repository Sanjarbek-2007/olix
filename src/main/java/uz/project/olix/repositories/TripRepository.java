package uz.project.olix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.project.olix.entity.Trip;

import java.util.List;
import uz.project.olix.entity.User;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {


    @Transactional
    @Modifying
    @Query("update Trip t set t.cargoOwner = ?1 where t.id = ?2")
    int updateCargoOwnerById(User cargoOwner, Long id);

//    List<Trip> findByTripStatus_Status(String inProgress);
}
