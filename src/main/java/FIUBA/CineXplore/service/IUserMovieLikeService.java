package FIUBA.CineXplore.service;

import FIUBA.CineXplore.model.UserMovieLike;

import java.util.List;

public interface IUserMovieLikeService {
    UserMovieLike likeMovie(Long userId, Long movieId);

    void unlikeMovie(Long userId, Long movieId);

    List<UserMovieLike> getLikesByUser(Long userId);

    List<UserMovieLike> getLikesByMovie(Long movieId);
}