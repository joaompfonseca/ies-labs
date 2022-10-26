package ies.lab3.e3.moviequotes.controller;

import java.util.*;

import ies.lab3.e3.moviequotes.dto.MovieDto;
import ies.lab3.e3.moviequotes.dto.QuoteDto;
import ies.lab3.e3.moviequotes.service.MovieQuotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class MovieQuotesController {

    @Autowired
    private MovieQuotesService movieQuotesService;

    @DeleteMapping("/resetdb")
    public ResponseEntity<Boolean> resetDatabase() { return movieQuotesService.resetDatabase(); }

    @GetMapping("/quote")
    public ResponseEntity<QuoteDto> getRandomQuote() {
        return movieQuotesService.getRandomQuote();
    }

    @GetMapping("/shows")
    public ResponseEntity<List<MovieDto>> getAllMoviesWithQuotes() {
        return movieQuotesService.getAllMoviesWithQuotes();
    }

    @GetMapping("/quotes")
    public ResponseEntity<QuoteDto> getRandomQuoteByMovieId(@RequestParam(value = "show") Long id) {
        return movieQuotesService.getRandomQuoteByMovieId(id);
    }

    @PostMapping("/quotes")
    public ResponseEntity<QuoteDto> createQuote(@RequestBody QuoteDto quote) {
        return movieQuotesService.createQuote(quote);
    }

    @PostMapping("/shows")
    public ResponseEntity<MovieDto> createMovie(@RequestBody MovieDto movie) {
        return movieQuotesService.createMovie(movie);
    }
}