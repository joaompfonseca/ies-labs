package ies.lab3.e3.moviequotes.repository;

import ies.lab3.e3.moviequotes.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT DISTINCT quote.movie FROM Quote quote")
    List<Movie> findAllMoviesWithQuotes();
}
