package uz.project.olix.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.project.olix.entity.Cargo;
import uz.project.olix.service.CargoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cargos")
@AllArgsConstructor
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
}
