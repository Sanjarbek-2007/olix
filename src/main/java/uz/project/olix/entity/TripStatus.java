package uz.project.olix.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TripStatus {
    private Float x;//широта
    private Float y;//долгота
    private String pozitionName;//Примерное название места
}
