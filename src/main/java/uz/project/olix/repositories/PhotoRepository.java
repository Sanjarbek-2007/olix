package uz.project.olix.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.project.olix.entity.Photo;
@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO users_profile_photos (user_id, profile_photos_id) values( :userId, :id )")
    void addPhotoByPhotoIdAndUserId(Long id, Long userId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO document_document_photos (document_id, document_photos_id) values( :docId, :photoId )")
    void addPhotoByDocumentIdAndPhotoId(@Param("docId") Long docId, @Param("photoId")Long photoId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO  truck_photo (truck_id , photo_id) values( :truckId, :photoId )")
    void addPhotoByPhotoIdAndTruckId(Long photoId, Long truckId);
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO cargo_photos (cargo_id, photos_id) values( :cargoId, :photoId )")
    void addPhotoByPhotoIdAndCargoId(Long photoId, Long cargoId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "delete from cargo_photos where cargo_id = :cargoId;")
    void deleteByCargoId(@Param("cargoId") Long cargoId);


    @Override
    void deleteById(Long aLong);
}