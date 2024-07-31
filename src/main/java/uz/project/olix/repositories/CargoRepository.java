package uz.project.olix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.project.olix.entity.Cargo;
import uz.project.olix.entity.Truck;

import java.util.List;
import uz.project.olix.entity.User;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {
    List<Cargo> findByName(String name);
    List<Cargo> findByWeightBetween(double minWeight, double maxWeight);
    List<Cargo> findByOwnerId(Long ownerId);
    List<Cargo> findByStatus(String status);

    List<Cargo> findByOwner_PhoneNumber(String phoneNumber);

    @Transactional
    @Modifying
    @Query(nativeQuery = true , value = "update cargo set owner_id = :ownerId where id = :id;")
    int updateOwnerById(@Param("ownerId") Long ownerId, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Cargo c WHERE c.truck.id = :truckId")
    void deleteByTruckId(@Param("truckId") Long truckId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Cargo c WHERE c.owner.id = :userId")
    void deleteByOwnerId(@Param("userId") Long userId);

}
