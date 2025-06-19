package FIUBA.CineXplore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
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

    @NotBlank
    @Size(max = 150)
    @Column(length = 150, nullable = false)
    private String fullName;

    // Descomentar si birthDate es obligatorio:
    // @NotNull
    private LocalDate birthDate;

    @ManyToMany(mappedBy = "directors")
    private Set<Movie> movies = new HashSet<>();
}