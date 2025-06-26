package FIUBA.CineXplore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
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
    private Long actorId;

    @NotBlank
    @Size(max = 150)
    @Column(length = 150, nullable = false)
    private String fullName;
    
    @NotNull
    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate birthDate;

    @ManyToMany(mappedBy = "actors")
    private Set<Movie> movies = new HashSet<>();
}