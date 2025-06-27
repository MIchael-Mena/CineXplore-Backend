package FIUBA.CineXplore.dto;

import FIUBA.CineXplore.model.Genre;
import FIUBA.CineXplore.model.Movie;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class MovieResponseDTO {
    public Long movieId;
    public String title;
    public String coverUrl;
    public String description;
    public Integer durationMin;
    public LocalDate releaseDate;
    public BigDecimal averageRating;
    public LocalDateTime createdAt;
    public Set<ActorDTO> actors;
    public Set<DirectorDTO> directors;
    public Set<String> genres;

    public static MovieResponseDTO fromMovie(Movie movie) {
        MovieResponseDTO dto = new MovieResponseDTO();
        dto.movieId = movie.getMovieId();
        dto.title = movie.getTitle();
        dto.description = movie.getDescription();
        dto.durationMin = movie.getDurationMin();
        dto.releaseDate = movie.getReleaseDate();
        dto.averageRating = movie.getAverageRating();
        dto.createdAt = movie.getCreatedAt();
        dto.coverUrl = movie.getCoverUrl();

        dto.directors = movie.getDirectors() != null
                ? movie.getDirectors().stream().map(DirectorDTO::toDirectorDTO).collect(Collectors.toSet())
                : Collections.emptySet();

        dto.actors = movie.getActors() != null
                ? movie.getActors().stream().map(ActorDTO::toActorDTO).collect(Collectors.toSet())
                : Collections.emptySet();

        dto.genres = movie.getGenres() != null
                ? movie.getGenres().stream().map(Genre::getName).collect(Collectors.toSet())
                : Collections.emptySet();

        return dto;
    }
}

