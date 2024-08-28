package uz.project.olix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.project.olix.entity.Chat;
import uz.project.olix.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {


    Chat findByUniqueIdentifier(String uniqueIdentifier);
    List<Chat> findByUser1_IdOrUser2_Id(Long user1Id, Long user2Id);
//    Optional<Chat> findById(Long chatId);
    boolean existsByUser1AndUser2(User user1, User user2);
    Optional<Chat> findByUser1AndUser2(User user1, User user2);


}
