package uz.project.olix.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double weight;
    private String status;
    @ManyToOne
    private User owner;

    @ManyToMany
    private List<Photo> photos;

    public Cargo(String name, double weight, String status  ) {
        this.name = name;
        this.weight = weight;
        this.status = status;

    }

    public Cargo(String name, String status, User owner, double weight) {
        this.name = name;
        this.status = status;
        this.owner = owner;
        this.weight = weight;
    }
}