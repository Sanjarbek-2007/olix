package uz.project.olix.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.project.olix.entity.Document;
import uz.project.olix.entity.Photo;
import uz.project.olix.exeptions.FileUploadFailedException;
import uz.project.olix.repositories.DocumentRepository;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final PhotoService photoService;
    public Document addDocument(Document doc, List<MultipartFile> multipartFiles) {
        Document document = documentRepository.save(doc);
        List<Photo> photos = new ArrayList<>();
        try {
            photos = photoService.addDocumentPhotos(multipartFiles, document.getId());
            document.setDocumentPhotos(photos);
        } catch (FileUploadFailedException e) {
            throw new RuntimeException(e);
        }
        return document;
    }
}
