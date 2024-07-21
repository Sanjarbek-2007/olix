package uz.project.olix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.project.olix.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
