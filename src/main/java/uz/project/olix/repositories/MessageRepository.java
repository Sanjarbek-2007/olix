//package uz.project.olix.repositories;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import uz.project.olix.entity.Message;
//import uz.project.olix.entity.User;
//
//import java.util.List;
//
//@Repository
//public interface MessageRepository extends JpaRepository<Message, Long> {
//    List<Message> findBySenderAndReceiver(User sender, User receiver);
//}