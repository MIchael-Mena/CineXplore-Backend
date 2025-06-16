package FIUBA.CineXplore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "UserMovieComment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class UserMovieComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String commentText;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    // Possible sentiment analysis score
    @Column(precision = 3, scale = 2)
    private BigDecimal sentimentScore;

    @Column(columnDefinition = "TEXT")
    private String aiTopics;

}