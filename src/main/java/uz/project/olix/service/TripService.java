package uz.project.olix.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.project.olix.dto.StartTripDto;
import uz.project.olix.entity.Trip;
import uz.project.olix.entity.User;
import uz.project.olix.repositories.TripRepository;

import java.util.List;
import java.util.Optional;
import uz.project.olix.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public Optional<Trip> getTripById(Long id) {
        return tripRepository.findById(id);
    }

    public ResponseEntity<Trip> saveTrip(StartTripDto trip) {
        String phoneNum = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByPhoneNumber(phoneNum).get();
        return new ResponseEntity<>(tripRepository.save(Trip.builder().departure(trip.departure()).destination(trip.destination()).driver(user).build()), HttpStatus.OK);
    }

    public Optional<Trip> updateTrip(Long id, Trip tripDetails) {
        return tripRepository.findById(id).map(trip -> {
            trip.setDriver(tripDetails.getDriver());
            trip.setCargoOwner(tripDetails.getCargoOwner());
            trip.setTripStatus(tripDetails.getTripStatus());
            trip.setDriverCoordinates(tripDetails.getDriverCoordinates());
            return tripRepository.save(trip);
        });
    }

    public boolean deleteTrip(Long id) {
        return tripRepository.findById(id).map(trip -> {
            tripRepository.delete(trip);
            return true;
        }).orElse(false);
    }

//    public void updateTripCoordinates() {
//        List<Trip> ongoingTrips = tripRepository.findByTripStatus_Status("In Progress");
//        ongoingTrips.forEach(trip -> {
//            trip.getDriverCoordinates().setX((float) (trip.getDriverCoordinates().getX() + 0.01));
//            trip.getDriverCoordinates().setY((float) (trip.getDriverCoordinates().getY() + 0.01));
//            tripRepository.save(trip);
//        });
//    }


}
