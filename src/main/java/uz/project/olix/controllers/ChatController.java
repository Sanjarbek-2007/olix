package uz.project.olix.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.project.olix.entity.Chat;
import uz.project.olix.service.ChatService;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/add")
    public ResponseEntity<Chat> createChat(@RequestParam Long user1Id, @RequestParam Long user2Id) {
        Chat chat = chatService.createChat(user1Id, user2Id);
        return ResponseEntity.ok(chat);
    }

    @GetMapping("/view")
    public ResponseEntity<List<Chat>> getChats(@RequestParam Long userId) {
        List<Chat> chats = chatService.findAllByUserId(userId);
        return ResponseEntity.ok(chats);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<Chat> getChat(@PathVariable Long chatId) {
        Chat chat = chatService.findById(chatId);
        return ResponseEntity.ok(chat);
    }
}
