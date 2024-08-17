package uz.project.olix.dto;

import lombok.Data;

@Data
public class SendMessageRequest {
    private Long senderId;
    private Long receiverId;
    private String content;
}
