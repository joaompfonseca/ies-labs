package ies.lab3.e3.moviequotes.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "year", nullable = false)
    private Integer year;
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL) // Propagates actions to children (quotes)
    private List<Quote> quotes;

    public Movie(String title, Integer year, List<Quote> quotes) {
        this.title = title;
        this.year = year;
        this.quotes = quotes;
    }

}
