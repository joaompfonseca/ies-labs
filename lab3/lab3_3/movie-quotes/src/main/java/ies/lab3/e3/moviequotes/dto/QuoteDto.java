package ies.lab3.e3.moviequotes.dto;

public class QuotePayload {
    private Long movieId;
    private String quote;

    public QuotePayload(Long movieId, String quote) {
        this.movieId = movieId;
        this.quote = quote;
    }

    public Long getMovieId() {
        return movieId;
    }

    public String getQuote() {
        return quote;
    }
}
