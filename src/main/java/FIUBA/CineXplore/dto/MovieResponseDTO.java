package FIUBA.CineXplore.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class MovieResponseDTO {
    public Long movieId;
    public String title;
    public String description;
    public Integer durationMin;
    public LocalDate releaseDate;
    public BigDecimal averageRating;
    public LocalDateTime createdAt;
    public Set<String> directors;
    public Set<String> genres;
    public Set<String> actors;
}