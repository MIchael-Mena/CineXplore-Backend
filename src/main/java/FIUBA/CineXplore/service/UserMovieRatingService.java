package FIUBA.CineXplore.service;

import FIUBA.CineXplore.model.UserMovieRating;
import FIUBA.CineXplore.repository.UserMovieRatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMovieRatingService implements IGenericService<UserMovieRating> {

    private final UserMovieRatingRepository userMovieRatingRepository;

    public UserMovieRatingService(UserMovieRatingRepository userMovieRatingRepository) {
        this.userMovieRatingRepository = userMovieRatingRepository;
    }

    @Override
    public UserMovieRating save(UserMovieRating userMovieRating) {
        return userMovieRatingRepository.save(userMovieRating);
    }

    @Override
    public UserMovieRating findById(Long id) {
        return userMovieRatingRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        userMovieRatingRepository.deleteById(id);
    }

    @Override
    public List<UserMovieRating> findAll() {
        return userMovieRatingRepository.findAll();
    }
}
