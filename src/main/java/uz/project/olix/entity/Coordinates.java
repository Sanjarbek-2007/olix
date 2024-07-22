package uz.project.olix.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Coordinates {
    private Float x; // широта
    private Float y; // долгота
    private String positionName; // Примерное название места
}