package uz.project.olix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.project.olix.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true , value = "INSERT INTO truck_documents (truck_id, documents_id) values ( :truckId, :docId) ")
    public void setDocsToTrucksById(@Param("truckId") Long truckId, @Param("docId") Long docId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Document d WHERE d.owner.id = :userId")
    void deleteByOwnerId(@Param("userId") Long userId);
}
