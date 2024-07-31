package uz.project.olix.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.project.olix.dto.SendMessageRequest;
import uz.project.olix.entity.Message;
import uz.project.olix.entity.User;
import uz.project.olix.service.MessageService;
import uz.project.olix.service.UserService;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Message> sendMessage(@RequestBody SendMessageRequest sendMessageRequest) {
        User sender = userService.findById(sendMessageRequest.getSenderId()).orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userService.findById(sendMessageRequest.getReceiverId()).orElseThrow(() -> new RuntimeException("Receiver not found"));
        return ResponseEntity.ok(messageService.sendMessage(sender, receiver, sendMessageRequest.getContent()));
    }
}
