package uz.project.olix.controllers;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.project.olix.entity.City;
import uz.project.olix.repositories.CityRepository;
import uz.project.olix.service.CityService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cities")
public class CityController {
    private final CityService cityService;
    @GetMapping("/search-city")
    public ResponseEntity<List<City>> search(@RequestParam String city) {
        return cityService.search(city);
    }
    @GetMapping("/search-country")
    public ResponseEntity<List<City>> searchCountry(@RequestParam String country) {
        return cityService.searchCountry(country);
    }
}
