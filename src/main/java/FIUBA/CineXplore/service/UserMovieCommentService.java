package FIUBA.CineXplore.service;

import FIUBA.CineXplore.model.UserMovieComment;
import FIUBA.CineXplore.repository.UserMovieCommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMovieCommentService implements IGenericService<UserMovieComment> {

    private final UserMovieCommentRepository userMovieCommentRepository;

    public UserMovieCommentService(UserMovieCommentRepository userMovieCommentRepository) {
        this.userMovieCommentRepository = userMovieCommentRepository;
    }

    @Override
    public UserMovieComment save(UserMovieComment userMovieComment) {
        return userMovieCommentRepository.save(userMovieComment);
    }

    @Override
    public UserMovieComment findById(Long id) {
        return userMovieCommentRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        userMovieCommentRepository.deleteById(id);
    }

    @Override
    public List<UserMovieComment> findAll() {
        return userMovieCommentRepository.findAll();
    }
}
