package uz.project.olix.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import uz.project.olix.dto.BecomeDriverDto;
import uz.project.olix.entity.Photo;
import uz.project.olix.entity.Truck;
import uz.project.olix.entity.User;
import uz.project.olix.exeptions.FileUploadFailedException;
import uz.project.olix.repositories.TruckRepository;
import uz.project.olix.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class TruckService {

    private final TruckRepository truckRepository;
    private final PhotoService photoService;

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
        if (dto.driverLicence().isEmpty()) {
            Truck truck =truckRepository.save( new Truck(dto.model(), user, dto.body()));
            try {
                List<Photo> photos = photoService.saveTruckPhoto(dto.photos(), truck.getId());
                if (!photos.isEmpty())return false;
                return true;
            } catch (FileUploadFailedException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

}
