package uz.project.olix.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.project.olix.dto.SendMessageRequest;
import uz.project.olix.entity.Message;
import uz.project.olix.entity.User;
import uz.project.olix.service.MessageService;
import uz.project.olix.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody SendMessageRequest sendMessageRequest) {
        User sender = userService.findById(sendMessageRequest.getSenderId()).orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userService.findById(sendMessageRequest.getReceiverId()).orElseThrow(() -> new RuntimeException("Receiver not found"));
        return ResponseEntity.ok(messageService.sendMessage(sender, receiver, sendMessageRequest.getContent()));
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable Long chatId) {
        List<Message> messages = messageService.getMessagesByChatId(chatId);
        return ResponseEntity.ok(messages);
    }
}
