package uz.project.olix.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.project.olix.entity.Trip;
import uz.project.olix.repositories.TripRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public Optional<Trip> getTripById(Long id) {
        return tripRepository.findById(id);
    }

    public Trip saveTrip(Trip trip) {
        return tripRepository.save(trip);
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

    public void updateTripCoordinates() {
        List<Trip> ongoingTrips = tripRepository.findByTripStatus_Status("In Progress");
        ongoingTrips.forEach(trip -> {
            trip.getDriverCoordinates().setX((float) (trip.getDriverCoordinates().getX() + 0.01));
            trip.getDriverCoordinates().setY((float) (trip.getDriverCoordinates().getY() + 0.01));
            tripRepository.save(trip);
        });
    }

    
}
