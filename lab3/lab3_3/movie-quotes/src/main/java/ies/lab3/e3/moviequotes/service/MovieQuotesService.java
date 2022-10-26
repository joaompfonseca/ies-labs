package ies.lab3.e3.moviequotes.service;

import ies.lab3.e3.moviequotes.model.Movie;
import ies.lab3.e3.moviequotes.model.Quote;
import ies.lab3.e3.moviequotes.repository.MovieRepository;
import ies.lab3.e3.moviequotes.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class MovieQuotesService {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private QuoteRepository quoteRepository;

    private Random rand = new Random();

    public Quote getRandomQuote() {
        List<Quote> quotes = quoteRepository.findAll();
        return quotes.get(rand.nextInt(quotes.size()));
    }


    public List<Movie> getAllMoviesWithQuotes() {
        return movieRepository.findAllMoviesWithQuotes();
    }
    public Quote getRandomQuoteByMovieId(Long id) {
        List<Quote> quotes = quoteRepository.findByMovieId(id);
        return quotes.get(rand.nextInt(quotes.size()));
    }
}
