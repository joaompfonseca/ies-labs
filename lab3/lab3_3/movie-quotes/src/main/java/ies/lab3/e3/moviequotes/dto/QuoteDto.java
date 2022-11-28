package ies.lab3.e3.moviequotes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuoteDto {
    private String quote;
    private Long movieId;
}
