package br.com.enzohonorato.filmsystem.domain.userfilm;

import br.com.enzohonorato.filmsystem.domain.film.Film;
import br.com.enzohonorato.filmsystem.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserFilm {
    @EmbeddedId
    private UserFilmKey id;

    public UserFilm() {
    }

    public UserFilm(UserFilmKey id, User user, Film film) {
        this.id = id;
        this.user = user;
        this.film = film;
    }

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @MapsId("filmId")
    @JoinColumn(name = "film_id")
    private Film film;

    private int rating;
}
