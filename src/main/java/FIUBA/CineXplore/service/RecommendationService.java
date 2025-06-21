package FIUBA.CineXplore.service;

import FIUBA.CineXplore.model.*;
import FIUBA.CineXplore.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final UserMovieLikeService likeService;
    private final UserMovieRatingService ratingService;
    private final MovieRepository movieRepository;

    public RecommendationService(UserMovieLikeService likeService, UserMovieRatingService ratingService, MovieRepository movieRepository) {
        this.likeService = likeService;
        this.ratingService = ratingService;
        this.movieRepository = movieRepository;
    }

    public List<Movie> recommendMoviesForUser(Long userId) {
        List<UserMovieLike> likes = likeService.getLikesByUser(userId);
        List<UserMovieRating> ratings = ratingService.getRatingsByUser(userId);

        Map<String, Integer> genreCount = new HashMap<>();
        Map<String, Integer> directorCount = new HashMap<>();
        Map<String, Integer> actorCount = new HashMap<>();

        processLikes(likes, genreCount, directorCount, actorCount);
        processHighRatings(ratings, genreCount, directorCount, actorCount, 4);

        List<String> topGenres = getTopKeys(genreCount, 3);
        List<String> topDirectors = getTopKeys(directorCount, 3);
        List<String> topActors = getTopKeys(actorCount, 3);

        Set<Long> seenMovieIds = getSeenMovieIds(likes, ratings);

        return getRecommendedMovies(topGenres, topDirectors, topActors, seenMovieIds);
    }

    private void processLikes(List<UserMovieLike> likes, Map<String, Integer> genreCount,
                              Map<String, Integer> directorCount, Map<String, Integer> actorCount) {
        for (UserMovieLike like : likes) {
            processMovie(like.getMovie(), genreCount, directorCount, actorCount);
        }
    }

    private void processHighRatings(List<UserMovieRating> ratings, Map<String, Integer> genreCount,
                                    Map<String, Integer> directorCount, Map<String, Integer> actorCount, int minUserRating) {
        for (UserMovieRating rating : ratings) {
            if (rating.getRating() >= minUserRating) {
                processMovie(rating.getMovie(), genreCount, directorCount, actorCount);
            }
        }
    }

    private void processMovie(Movie movie, Map<String, Integer> genreCount,
                              Map<String, Integer> directorCount, Map<String, Integer> actorCount) {
        for (Genre genre : movie.getGenres()) {
            genreCount.put(genre.getName(), genreCount.getOrDefault(genre.getName(), 0) + 1);
        }
        for (Director director : movie.getDirectors()) {
            directorCount.put(director.getFullName(), directorCount.getOrDefault(director.getFullName(), 0) + 1);
        }
        for (Actor actor : movie.getActors()) {
            actorCount.put(actor.getFullName(), actorCount.getOrDefault(actor.getFullName(), 0) + 1);
        }
    }

    private List<String> getTopKeys(Map<String, Integer> countMap, int limit) {
        return countMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(limit)
                .map(Map.Entry::getKey)
                .toList();
    }

    private Set<Long> getSeenMovieIds(List<UserMovieLike> likes, List<UserMovieRating> ratings) {
        Set<Long> seenMovieIds = new HashSet<>();
        likes.forEach(l -> seenMovieIds.add(l.getMovie().getMovieId()));
        ratings.forEach(r -> seenMovieIds.add(r.getMovie().getMovieId()));
        return seenMovieIds;
    }

    private List<Movie> getRecommendedMovies(List<String> topGenres, List<String> topDirectors, List<String> topActors, Set<Long> seenMovieIds) {
        return movieRepository.findAll().stream()
                .filter(m -> m.getGenres().stream().map(Genre::getName).anyMatch(topGenres::contains)
                        || m.getDirectors().stream().map(Director::getFullName).anyMatch(topDirectors::contains)
                        || m.getActors().stream().map(Actor::getFullName).anyMatch(topActors::contains))
                .filter(m -> !seenMovieIds.contains(m.getMovieId()))
                .limit(10)
                .collect(Collectors.toList());
    }
}