package FIUBA.CineXplore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @NotBlank
    @Size(max = 2000)
    @Column(columnDefinition = "TEXT", nullable = false)
    private String commentText;

    @NotNull
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    @Digits(integer = 1, fraction = 2)
    @Column(precision = 3, scale = 2)
    private BigDecimal sentimentScore;

    @Column(columnDefinition = "TEXT")
    private String aiTopics;
}