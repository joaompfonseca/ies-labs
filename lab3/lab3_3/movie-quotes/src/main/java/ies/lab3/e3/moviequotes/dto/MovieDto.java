package ies.lab3.e3.moviequotes.dto;

public class MoviePayload {
    private Long movieId;
    private String title;
    private Integer year;

    public MoviePayload(Long movieId, String title, Integer year) {
        this.movieId = movieId;
        this.title = title;
        this.year = year;
    }

    public Long getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public Integer getYear() {
        return year;
    }
}
