package uz.project.olix;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.web.client.RestTemplate;
import uz.project.olix.entity.City;
import uz.project.olix.repositories.CityRepository;

@SpringBootApplication
@Slf4j
//@RequiredArgsConstructor
public class OlixApplication {

//    private final CityRepository cityRepository;

    public static void main(String[] args) {
        SpringApplication.run(OlixApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner commandLineRunner() {
//
//        return args -> {
//            ObjectMapper objectMapper = new ObjectMapper();
//            try {
//
//                if(cityRepository.findAll().isEmpty()) {
//
//                    File jsonFile = Paths.get("src/main/resources/static/cities.json").toFile();
//                    City[] cities = objectMapper.readValue(jsonFile, City[].class);
////                    log.info("Cities are adding{} !!!!!!!!!", Arrays.toString(cities));
//                    cityRepository.saveAll(List.of(cities));
////                    log.info("Cities are added{} !!!!!!!!!", Arrays.toString(cities));
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        };
//    }
}
