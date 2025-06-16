package FIUBA.CineXplore.service;

import FIUBA.CineXplore.model.UserMovieLike;
import FIUBA.CineXplore.model.UserMovieLikeId;
import FIUBA.CineXplore.repository.UserMovieLikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMovieLikeService implements IUserMovieLikeService {

    private final UserMovieLikeRepository userMovieLikeRepository;

    public UserMovieLikeService(UserMovieLikeRepository userMovieLikeRepository) {
        this.userMovieLikeRepository = userMovieLikeRepository;
    }

    @Override
    public UserMovieLike save(UserMovieLike userMovieLike) {
        return userMovieLikeRepository.save(userMovieLike);
    }

    @Override
    public UserMovieLike findById(UserMovieLikeId id) {
        return userMovieLikeRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(UserMovieLikeId id) {
        userMovieLikeRepository.deleteById(id);
    }

    @Override
    public List<UserMovieLike> findAll() {
        return userMovieLikeRepository.findAll();
    }
}
