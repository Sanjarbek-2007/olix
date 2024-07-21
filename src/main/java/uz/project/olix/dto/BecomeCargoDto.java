package uz.project.olix.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BecomeCargoDto {
    private String name;
    private double weight;
    private Long ownerId;
    private List<MultipartFile> photos;
}
