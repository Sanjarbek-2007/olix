//package uz.project.olix.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import uz.project.olix.entity.Chat;
//import uz.project.olix.entity.User;
//import uz.project.olix.repositories.ChatRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class ChatService {
//    @Autowired
//    private ChatRepository chatRepository;
//
//    public Chat createChat(User user1, User user2) {
//        Chat chat = new Chat();
//        chat.setUser1(user1);
//        chat.setUser2(user2);
//        return chatRepository.save(chat);
//    }
//
//    public Chat findById(Long chatId) {
//        return chatRepository.findById(chatId).orElseThrow(() -> new RuntimeException("Chat not found"));
//    }
//
//    public List<Chat> findAllByUserId(Long userId) {
//        return chatRepository.findAllByUser1_IdOrUser2_Id(userId, userId);
//    }
//}
