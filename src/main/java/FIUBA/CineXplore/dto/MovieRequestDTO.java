package FIUBA.CineXplore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Set;

public class MovieRequestDTO {
    @NotBlank
    @Size(max = 200)
    public String title;

    @Size(max = 500)
    public String coverUrl;

    @Size(max = 1000)
    public String description;

    @NotNull
    @Positive
    public Integer durationMin;

    @NotNull
    public LocalDate releaseDate;

    public Set<Long> directorIds;
    public Set<Long> genreIds;
    public Set<Long> actorIds;

}