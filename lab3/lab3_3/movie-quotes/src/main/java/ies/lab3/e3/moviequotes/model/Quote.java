package ies.lab3.e3.moviequotes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "quotes")
@Data
@NoArgsConstructor
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "content", nullable = false)
    private String content;
    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false) // Foreign Key that references the movie
    private Movie movie;

    public Quote(String content, Movie movie) {
        this.content = content;
        this.movie = movie;
    }
}
