package uz.project.olix.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BecomeCustomerDto {
    private String documentSerial;
    private String documentType;
    private List<MultipartFile> documentPhotos;
}
