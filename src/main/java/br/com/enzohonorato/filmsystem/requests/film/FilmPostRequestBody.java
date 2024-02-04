package br.com.enzohonorato.filmsystem.requests.film;

import br.com.enzohonorato.filmsystem.domain.genre.Genre;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FilmPostRequestBody {
    @Schema(description = "Nome do filme", example = "Tropa de Elite")
    private String name;
    @Schema(description = "Duração em minutos do filme", example = "100")
    private Long minutes;
    @Schema(description = "Data de lançamento do filme (YYYY-MM-DD)", example = "2007-02-03")
    private LocalDate releaseDate;
    @Schema(description = "Objeto contendo o id do gênero do filme", example = "{id: 2}")
    private Genre genre;
}
