package uz.project.olix.controllers;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import uz.project.olix.dto.BecomeCargoDto;
import uz.project.olix.entity.Cargo;
import uz.project.olix.entity.Photo;
import uz.project.olix.entity.Truck;
import uz.project.olix.entity.User;
import uz.project.olix.service.CargoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cargos")
@RequiredArgsConstructor
public class CargoController {

    private final CargoService cargoService;


    @GetMapping
    public List<Cargo> getAllCargos() {
        return cargoService.getAllCargos();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Cargo> getCargoById(@PathVariable Long id) {
        Optional<Cargo> cargo = cargoService.getCargoById(id);
        return cargo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cargo> createCargo(@RequestBody Cargo cargo) {
        Cargo savedCargo = cargoService.saveCargo(cargo);
        return ResponseEntity.ok(savedCargo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cargo> updateCargo(@PathVariable Long id, @RequestBody Cargo cargoDetails) {
        Optional<Cargo> updatedCargo = cargoService.updateCargo(id, cargoDetails);
        return updatedCargo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCargo(@PathVariable Long id) {
        boolean isDeleted = cargoService.deleteCargo(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/name/{name}")
    public List<Cargo> getCargoByName(@PathVariable String name) {
        return cargoService.getCargoByName(name);
    }

    @GetMapping("/weight")
    public List<Cargo> getCargoByWeight(@RequestParam double minWeight, @RequestParam double maxWeight) {
        return cargoService.getCargoByWeight(minWeight, maxWeight);
    }

    @GetMapping("/truck/{truckId}")
    public List<Cargo> getCargoByTruck(@PathVariable Long truckId) {
        Truck truck = new Truck();
        truck.setId(truckId);
        return cargoService.getCargoByTruck(truck);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addCargo(@RequestBody BecomeCargoDto dto, @RequestParam Long truckId, @RequestParam Long userId) {
        Truck truck = new Truck();
        truck.setId(truckId);
        User user = new User();
        user.setId(userId);

        boolean isAdded = cargoService.addCargo(dto, user, truck);
        return isAdded ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping("/user/{userId}")
    public List<Cargo> getCargoByUser(@PathVariable Long userId) {
        return cargoService.getCargoByUser(userId);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteMultipleCargos(@RequestParam List<Long> ids) {
        boolean isDeleted = cargoService.deleteMultipleCargos(ids);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}/photos")
    public ResponseEntity<Void> updateCargoPhotos(@PathVariable Long id, @RequestBody List<Photo> photos) {
        boolean isUpdated = cargoService.updateCargoPhotos(id, photos);
        return isUpdated ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping("/status")
    public List<Cargo> getCargoByStatus(@RequestParam String status) {
        return cargoService.getCargoByStatus(status);
    }
}
