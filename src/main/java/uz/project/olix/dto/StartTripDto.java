package uz.project.olix.dto;

import jakarta.persistence.Embedded;
import jakarta.persistence.ManyToOne;
import uz.project.olix.entity.City;
import uz.project.olix.entity.TripStatus;

public record StartTripDto(
        String departure,
        String destination
) {

}
