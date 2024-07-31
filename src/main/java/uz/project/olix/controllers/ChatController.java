//package uz.project.olix.controllers;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import uz.project.olix.entity.Chat;
//import uz.project.olix.entity.User;
//import uz.project.olix.service.ChatService;
//import uz.project.olix.service.UserService;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/chats")
//@RequiredArgsConstructor
//public class ChatController {
//
//    private final ChatService chatService;
//    private final UserService userService;
//
//    @PostMapping
//    public ResponseEntity<Chat> createChat(@RequestParam Long userId, @RequestParam Long otherUserId) {
//        User user = userService.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        User otherUser = userService.findById(otherUserId).orElseThrow(() -> new RuntimeException("Other user not found"));
//        return ResponseEntity.ok(chatService.createChat(user, otherUser));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Chat>> getChats(@RequestParam Long userId) {
//        if (userId == null) {
//            return ResponseEntity.badRequest().body(null);
//        }
//        return ResponseEntity.ok(chatService.findAllByUserId(userId));
//    }
//
//    @GetMapping("/{chatId}")
//    public ResponseEntity<Chat> getChat(@PathVariable Long chatId) {
//        if (chatId == null) {
//            return ResponseEntity.badRequest().body(null);
//        }
//        return ResponseEntity.ok(chatService.findById(chatId));
//    }
//}
