package uz.project.olix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.project.olix.entity.Chat;
import uz.project.olix.entity.Message;
import uz.project.olix.entity.User;
import uz.project.olix.repositories.ChatRepository;
import uz.project.olix.repositories.MessageRepository;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRepository chatRepository;

    public List<Message> getMessagesByChatId(Long chatId) {
        List<Message> messages = messageRepository.findByChatId(chatId);
        System.out.println("Found " + messages.size() + " messages for chatId: " + chatId);
        return messages;
    }

    public Message sendMessage(User sender, User receiver, String content) {
        Chat chat = chatRepository.findByUser1AndUser2(sender, receiver)
                .orElseGet(() -> createChat(sender, receiver));

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setChat(chat);
        return messageRepository.save(message);
    }

    private Chat createChat(User user1, User user2) {
        Chat chat = new Chat();
        chat.setUser1(user1);
        chat.setUser2(user2);
        return chatRepository.save(chat);
    }
}
