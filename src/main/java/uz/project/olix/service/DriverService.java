package uz.project.olix.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uz.project.olix.entity.Driver;

import java.util.List;
import java.util.Optional;
import uz.project.olix.repositories.DriverRepository;

@Service
@AllArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;


    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public Optional<Driver> getDriverById(Long id) {
        return driverRepository.findById(id);
    }

    public Driver saveDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    public Optional<Driver> updateDriver(Long id, Driver driverDetails) {
        return driverRepository.findById(id).map(driver -> {
            driver.setName(driverDetails.getName());
            driver.setCar(driverDetails.getCar());
            return driverRepository.save(driver);
        });
    }

    public boolean deleteDriver(Long id) {
        return driverRepository.findById(id).map(driver -> {
            driverRepository.delete(driver);
            return true;
        }).orElse(false);
    }
}
