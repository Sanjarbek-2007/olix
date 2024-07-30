package uz.project.olix.service;

import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import uz.project.olix.dto.UploadCargoDto;
import uz.project.olix.dto.BecomeDriverDto;
import uz.project.olix.entity.Cargo;
import uz.project.olix.entity.Document;
import uz.project.olix.entity.Photo;
import uz.project.olix.entity.Truck;
import uz.project.olix.entity.User;
import uz.project.olix.exeptions.FileUploadFailedException;
import uz.project.olix.repositories.CargoRepository;
import uz.project.olix.repositories.DocumentRepository;
import uz.project.olix.repositories.TruckRepository;

@Service
@RequiredArgsConstructor
public class TruckService {

    private final TruckRepository truckRepository;
    private final PhotoService photoService;
    private final DocumentService documentService;
    private final CargoService cargoService;
    private final CargoRepository cargoRepository;
    private final DocumentRepository documentRepository;

    public List<Truck> getAllTrucks() {
        return truckRepository.findAll();
    }

    public Optional<Truck> getTruckById(Long id) {
        return truckRepository.findById(id);
    }

    public Truck saveTruck(Truck truck) {
        return truckRepository.save(truck);
    }

    public Optional<Truck> updateTruck(Long id, Truck truckDetails) {
        return truckRepository.findById(id).map(truck -> {
            truck.setModel(truckDetails.getModel());
            truck.setOwner(truckDetails.getOwner());
//            truck.setTechnicalPassport(truckDetails.getTechnicalPassport());
            truck.setCargos(truckDetails.getCargos());
            return truckRepository.save(truck);
        });
    }

    public boolean deleteTruck(Long id) {
        return truckRepository.findById(id).map(truck -> {
            truckRepository.delete(truck);
            return true;
        }).orElse(false);
    }

    public boolean addTruck(BecomeDriverDto dto, User user) {
        if (!dto.carNum().isEmpty()) {

            Document technicalPassport = documentService.addDocument(new Document(dto.drivingLicenceSerialNumber(), "Technical Passport", user), dto.carDocs());
            Truck truck = truckRepository.save(new Truck(dto.model(), user, dto.body(), technicalPassport, dto.carNum()));
//            documentRepository.setDocsToTrucksById(truck.getId(),technicalPassport.getId());
            try {
                List<Photo> photos = photoService.saveTruckPhoto(dto.photos(), truck.getId());
                return !photos.isEmpty();
            } catch (FileUploadFailedException e) {
                throw new RuntimeException(e);
            }
        }

        return false;
    }

    public ResponseEntity<List<Cargo>> uploadCargo(UploadCargoDto dto) {
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        Truck truck = truckRepository.findByOwner_PhoneNumber(phoneNumber);
        List<Cargo> cargos = new ArrayList<>();
        for (Long cargoId : dto.cargoIds()) {
            cargoRepository.findById(cargoId).ifPresent(cargo -> {
                cargos.add(cargo);
                truckRepository.addCargoByTruckIdAndCargoId(truck.getId(), cargoId);
            });
        }
        if (!cargos.isEmpty()) {
            return new ResponseEntity<>(cargos, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
//    public ResponseEntity<?> removeCargo(RemoveCargoDto dto) {}
}
