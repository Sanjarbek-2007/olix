package uz.project.olix.repositories;

import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.project.olix.entity.Truck;
import uz.project.olix.entity.User;

public interface TruckRepository extends JpaRepository<Truck, Long> {
    boolean existsByOwner_PhoneNumber(String phoneNumber);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO truck_cargos (truck_id, cargos_id) values( :truckId, :cargoId )")
    void addCargoByTruckIdAndCargoId(Long truckId, Long cargoId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE from truck_cargos where truck_id = :truckId")
    void deleteAllCargosByTruckId(Long truckId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE from truck_cargos where cargos_id = :cargoId")
    void deleteCargosByCargoId(Long cargoId);

    Truck findByOwner_PhoneNumber(String phoneNumber);


}
