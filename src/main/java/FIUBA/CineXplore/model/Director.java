package FIUBA.CineXplore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Table(name = "Director")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long directorId;

    @Column(length = 150, nullable = false)
    private String fullName;

    private LocalDate birthDate;

    @OneToMany(mappedBy = "director")
    private Set<Movie> movies;

}