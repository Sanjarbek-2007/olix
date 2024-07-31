package uz.project.olix.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false) // Changed column name to "id"
    private Long id;

    @Column(name = "city_id", nullable = false)
    private Long city_id; // Renamed to cityId to avoid conflict

    private String city;
    private String country;

    public City(Long city_id, String city, String country) {
        this.city_id = city_id;
        this.city = city;
        this.country = country;
    }
}
