package uz.project.olix.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.project.olix.entity.Chat;
import uz.project.olix.entity.User;
import uz.project.olix.repositories.ChatRepository;
import uz.project.olix.repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public Chat createChat(Long user1Id, Long user2Id) {
        User user1 = userRepository.findById(user1Id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User user2 = userRepository.findById(user2Id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if chat already exists
        if (chatRepository.existsByUser1AndUser2(user1, user2) ||
                chatRepository.existsByUser1AndUser2(user2, user1)) {
            throw new RuntimeException("Chat already exists");
        }

        Chat chat = new Chat();
        chat.setUser1(user1);
        chat.setUser2(user2);
        chat.setUniqueIdentifier(createUniqueIdentifier(user1Id, user2Id));

        return chatRepository.save(chat);
    }

    private String createUniqueIdentifier(Long userId1, Long userId2) {
        return (userId1 < userId2) ? userId1 + "_" + userId2 : userId2 + "_" + userId1;
    }

    public List<Chat> findAllByUserId(Long userId) {
        return chatRepository.findByUser1_IdOrUser2_Id(userId, userId);
    }

    public Chat findById(Long chatId) {
        return chatRepository.findById(chatId).orElse(null);
    }
}
