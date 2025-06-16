package FIUBA.CineXplore.service;

import FIUBA.CineXplore.model.UserMovieLike;
import FIUBA.CineXplore.model.UserMovieLikeId;

import java.util.List;

public interface IUserMovieLikeService {
    UserMovieLike save(UserMovieLike userMovieLike);

    UserMovieLike findById(UserMovieLikeId id);

    void deleteById(UserMovieLikeId id);

    List<UserMovieLike> findAll();
}
