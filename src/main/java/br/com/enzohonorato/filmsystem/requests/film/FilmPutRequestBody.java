package br.com.enzohonorato.filmsystem.requests.film;

import br.com.enzohonorato.filmsystem.domain.genre.Genre;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FilmPutRequestBody {
    @Schema(description = "Id do filme a ser atualizado", example = "Tropa de Elite 2")
    private Long id;
    @Schema(description = "Novo nome do filme", example = "Tropa de Elite")
    private String name;
    @Schema(description = "Nova duração em minutos do filme", example = "120")
    private Long minutes;
    @Schema(description = "Nova data de lançamento do filme (YYYY-MM-DD)", example = "2010-02-03")
    private LocalDate releaseDate;
    @Schema(description = "Novo objeto contendo o id do gênero do filme", example = "{id: 2}")
    private Genre genre;
}
