package FIUBA.CineXplore.service;

import FIUBA.CineXplore.model.UserMovieComment;

import java.util.List;

public interface IUserMovieCommentService {
    UserMovieComment addComment(Long userId, Long movieId, String commentText);

    void deleteComment(Long commentId);

    List<UserMovieComment> getCommentsByUser(Long userId);

    List<UserMovieComment> getCommentsByMovie(Long movieId);

    UserMovieComment updateComment(Long commentId, String commentText);
}
