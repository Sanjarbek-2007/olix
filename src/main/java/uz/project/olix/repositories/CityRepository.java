package uz.project.olix.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.project.olix.entity.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {


    @Query("SELECT c FROM City c WHERE c.country LIKE %:keyword%")
    List<City> findCountriesByKeyword(@Param("keyword") String keyword);
    @Query("SELECT c FROM City c WHERE c.city LIKE %:keyword%")
    List<City> findCitiesByKeyword(@Param("keyword") String keyword);
}
