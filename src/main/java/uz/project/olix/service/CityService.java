package uz.project.olix.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.project.olix.entity.City;
import uz.project.olix.repositories.CityRepository;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
    public List<City> findAll() {
        return cityRepository.findAll();
    }
    public ResponseEntity<List<City>> search(String keyword) {

        return new ResponseEntity<>(cityRepository.findCitiesByKeyword(keyword), HttpStatus.OK);
    }

    public ResponseEntity<List<City>> searchCountry(String country) {

        return new ResponseEntity<>(cityRepository.findCitiesByKeyword(country), HttpStatus.OK);
    }
}
