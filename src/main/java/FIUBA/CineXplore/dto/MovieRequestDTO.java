package FIUBA.CineXplore.dto;

import java.time.LocalDate;
import java.util.Set;

public class MovieRequestDTO {
    public String title;
    public String description;
    public Integer durationMin;
    public LocalDate releaseDate;
    public Set<Long> directorIds;
    public Set<Long> genreIds;
    public Set<Long> actorIds;
}