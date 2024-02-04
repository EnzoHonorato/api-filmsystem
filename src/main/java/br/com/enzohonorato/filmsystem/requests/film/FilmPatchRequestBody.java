package br.com.enzohonorato.filmsystem.requests.film;

import br.com.enzohonorato.filmsystem.domain.genre.Genre;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FilmPatchRequestBody {
    private Long id;
    private String name;
    private Long minutes;
    private LocalDate releaseDate;
    private Genre genre;
}
