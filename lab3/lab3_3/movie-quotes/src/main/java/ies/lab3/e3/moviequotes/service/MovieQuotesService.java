package ies.lab3.e3.moviequotes.service;

import ies.lab3.e3.moviequotes.model.Movie;
import ies.lab3.e3.moviequotes.model.Quote;
import ies.lab3.e3.moviequotes.dto.MovieDto;
import ies.lab3.e3.moviequotes.dto.QuoteDto;
import ies.lab3.e3.moviequotes.repository.MovieRepository;
import ies.lab3.e3.moviequotes.repository.QuoteRepository;
import ies.lab3.e3.moviequotes.util.ImportData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class MovieQuotesService {

    private MovieRepository movieRepository;
    private QuoteRepository quoteRepository;
    private Random rand;

    @Autowired
    public MovieQuotesService(MovieRepository movieRepository, QuoteRepository quoteRepository) {
        this.movieRepository = movieRepository;
        this.quoteRepository = quoteRepository;
        this.rand = new Random();
    }

    public ResponseEntity<Boolean> resetDatabase() {
        try {
            // Clear all records on tables
            quoteRepository.deleteAll();
            movieRepository.deleteAll();
            // Insert tuples in tables
            List<Movie> movies = ImportData.fromFile("src/main/resources/static/data.csv");
            movieRepository.saveAll(movies); // Also inserts in the quotes table
            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<QuoteDto> getRandomQuote() {
        List<Quote> quotes = quoteRepository.findAll();
        if (quotes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Quote quote = quotes.get(rand.nextInt(quotes.size()));
        QuoteDto quoteDto = new QuoteDto(quote.getContent(), quote.getMovie().getId());
        return new ResponseEntity<>(quoteDto, HttpStatus.OK);
    }

    public ResponseEntity<List<MovieDto>> getAllMoviesWithQuotes() {
        List<Movie> movies = movieRepository.findAllMoviesWithQuotes();
        if (movies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<MovieDto> moviesDto = new ArrayList<>();
        for (Movie movie : movies) {
            moviesDto.add(new MovieDto(movie.getId(), movie.getTitle(), movie.getYear()));
        }
        return new ResponseEntity<>(moviesDto, HttpStatus.OK);
    }

    public ResponseEntity<QuoteDto> getRandomQuoteByMovieId(Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (!movie.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Quote> quotes = movie.get().getQuotes();
        if (quotes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Quote quote = quotes.get(rand.nextInt(quotes.size()));
        QuoteDto quoteDto = new QuoteDto(quote.getContent(), quote.getMovie().getId());
        return new ResponseEntity<>(quoteDto, HttpStatus.OK);
    }

    public ResponseEntity<QuoteDto> createQuote(@Valid QuoteDto quoteDto) {
        try {
            Optional<Movie> movie = movieRepository.findById(quoteDto.getMovieId());
            if (!movie.isPresent()) {
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY); // Foreign Key doesn't exist
            }
            Quote _quote = quoteRepository.save(new Quote(quoteDto.getQuote(), movie.get()));
            QuoteDto _quoteDto = new QuoteDto(_quote.getContent(), _quote.getMovie().getId());
            return new ResponseEntity<>(_quoteDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<MovieDto> createMovie(@Valid MovieDto movieDto) {
        try {
            Movie _movie = movieRepository.save(new Movie(movieDto.getTitle(), movieDto.getYear(), new ArrayList<>()));
            MovieDto _movieDto = new MovieDto(_movie.getId(), _movie.getTitle(), _movie.getYear());
            return new ResponseEntity<>(_movieDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
