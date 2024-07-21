package uz.project.olix.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String model;
    private String number;
    private String body;
    @OneToOne
    private User owner;

    @ManyToMany
    private List<Document> documents;
    @ManyToMany
    private List<Photo> photo;

    @ManyToMany
    private List<Cargo> cargos;

    public Truck(String model, User owner, String body ) {
        this.model = model;
        this.owner = owner;
        this.body = body;
        this.cargos = null;
    }
}
