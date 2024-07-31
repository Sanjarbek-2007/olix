package uz.project.olix.controllers;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.project.olix.dto.AddCargoDto;
import uz.project.olix.entity.Cargo;
import uz.project.olix.entity.Photo;
import uz.project.olix.service.CargoService;

@RestController
@RequestMapping("/cargos")
@RequiredArgsConstructor
public class CargoController {

    private final CargoService cargoService;


    @GetMapping
    public List<Cargo> getAllCargos() {
        return cargoService.getAllCargos();
    }
    @GetMapping("/my")
    public List<Cargo> getAllCargosByOwner() {
        return cargoService.getAllCargosByOwner();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Cargo> getCargoById(@PathVariable Long id) {
        Optional<Cargo> cargo = cargoService.getCargoById(id);
        return cargo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Cargo> createCargo(@ModelAttribute AddCargoDto cargo) {
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
        boolean isDeleted = cargoService.deleteCargo(id) ;
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
//
//    @GetMapping("/truck/{truckId}")
//    public List<Cargo> getCargoByTruck(@PathVariable Long truckId) {
//        Truck truck = new Truck();
//        truck.setId(truckId);
//        return cargoService.getCargoByTruck(truck);
//    }


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
