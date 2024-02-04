package br.com.enzohonorato.filmsystem.requests.userfilm;

import br.com.enzohonorato.filmsystem.domain.film.Film;
import br.com.enzohonorato.filmsystem.domain.userfilm.UserFilmKey;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserFilmListResponseDTO {
    private UserFilmKey id;
    private Film film;
    private int rating;
}
