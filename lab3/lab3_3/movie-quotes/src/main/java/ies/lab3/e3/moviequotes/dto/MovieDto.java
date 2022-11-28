package ies.lab3.e3.moviequotes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieDto {
    private Long id;
    private String title;
    private Integer year;
}
