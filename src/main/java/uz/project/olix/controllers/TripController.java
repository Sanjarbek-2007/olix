package uz.project.olix.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.project.olix.dto.StartTripDto;
import uz.project.olix.entity.Trip;
import uz.project.olix.service.TripService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {

    private static final Logger log = LoggerFactory.getLogger(TripController.class);
    private final TripService tripService;

    @GetMapping
    public List<Trip> getAllTrips() {
        return tripService.getAllTrips();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable Long id) {
        Optional<Trip> trip = tripService.getTripById(id);
        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Trip> createTrip(@ModelAttribute StartTripDto dto) {
        log.info("adding a trip from " +dto.departure()+ " to " +dto.destination());
        return  tripService.saveTrip(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable Long id, @RequestBody Trip tripDetails) {
        Optional<Trip> updatedTrip = tripService.updateTrip(id, tripDetails);
        return updatedTrip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        boolean isDeleted = tripService.deleteTrip(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

//     New endpoint to update coordinates of ongoing trips
//    @PutMapping("/update-coordinates")
//    public ResponseEntity<Void> updateTripCoordinates() {
//        tripService.updateTripCoordinates();
//        return ResponseEntity.ok().build();
//    }
}
