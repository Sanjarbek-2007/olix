package uz.project.olix.controllers;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import uz.project.olix.entity.Truck;
import uz.project.olix.service.TruckService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trucks")
@RequiredArgsConstructor
public class TruckController {

    private final TruckService truckService;

    @Secured({"ROLE_ADMIN", "ROLE_DRIVER"})
    @GetMapping
    public List<Truck> getAllTrucks() {
        return truckService.getAllTrucks();
    }

    @Secured({"ROLE_ADMIN", "ROLE_DRIVER"})
    @GetMapping("/{id}")
    public ResponseEntity<Truck> getTruckById(@PathVariable Long id) {
        Optional<Truck> truck = truckService.getTruckById(id);
        return truck.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Secured({"ROLE_ADMIN", "ROLE_DRIVER"})
    @PostMapping
    public ResponseEntity<Truck> createTruck(@RequestBody Truck truck) {
        Truck savedTruck = truckService.saveTruck(truck);
        return ResponseEntity.ok(savedTruck);
    }

    @Secured({"ROLE_ADMIN", "ROLE_DRIVER"})
    @PutMapping("/{id}")
    public ResponseEntity<Truck> updateTruck(@PathVariable Long id, @RequestBody Truck truckDetails) {
        Optional<Truck> updatedTruck = truckService.updateTruck(id, truckDetails);
        return updatedTruck.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Secured({"ROLE_ADMIN", "ROLE_DRIVER"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTruck(@PathVariable Long id) {
        boolean isDeleted = truckService.deleteTruck(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
