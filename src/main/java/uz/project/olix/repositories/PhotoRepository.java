package uz.project.olix.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.project.olix.entity.Photo;
@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO user_profile_photos (user_id, profile_photos_id) values( :userId, :id )")
    void addPhotoByPhotoIdAndUserId(Long id, Long userId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO  truck_photo (truck_id , photo_id) values( :truckId, :photoId )")
    void addPhotoByPhotoIdAndTruckId(Long photoId, Long truckId);
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO cargo_photo (cargo_id, photo_id) values( :cargoId, :photoId )")
    void addPhotoByPhotoIdAndCargoId(Long photoId, Long cargoId);
}