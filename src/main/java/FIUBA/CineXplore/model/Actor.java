package FIUBA.CineXplore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Table(name = "Actor")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer actorId;

    @Column(length = 150, nullable = false)
    private String fullName;

    private LocalDate birthDate;

    @ManyToMany(mappedBy = "actors")
    private Set<Movie> movies;

}