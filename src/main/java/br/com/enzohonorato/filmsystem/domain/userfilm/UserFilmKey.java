package br.com.enzohonorato.filmsystem.domain.userfilm;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserFilmKey implements Serializable {
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "film_id")
    private Long filmId;

    public UserFilmKey() {
    }

    public UserFilmKey(Long userId, Long filmId) {
        this.userId = userId;
        this.filmId = filmId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserFilmKey that = (UserFilmKey) o;
        return userId.equals(that.userId) && filmId.equals(that.filmId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, filmId);
    }
}
