package uz.project.olix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.project.olix.entity.Photo;
@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
}