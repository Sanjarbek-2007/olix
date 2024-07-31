package uz.project.olix.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.project.olix.entity.Photo;
import uz.project.olix.entity.Document;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String username;
    private String fullName;
    private String profilePicture;
    private String phoneNumber;
    private List<Photo> profilePhotos;
    private Boolean isConfirmed;
    private List<Document> documents;
}
