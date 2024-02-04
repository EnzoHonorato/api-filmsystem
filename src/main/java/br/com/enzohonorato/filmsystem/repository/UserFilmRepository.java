package br.com.enzohonorato.filmsystem.repository;

import br.com.enzohonorato.filmsystem.domain.userfilm.UserFilm;
import br.com.enzohonorato.filmsystem.domain.userfilm.UserFilmKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFilmRepository extends JpaRepository<UserFilm, UserFilmKey> {
    @Query(value = "select * from user_film where user_id = :userId", nativeQuery = true)
    List<UserFilm> listUserFilmList(@Param("userId") Long userId);

    @Modifying
    @Query(value = "update user_film set rating = :rating where user_id = :userId and film_id = :filmId", nativeQuery = true)
    void updateUserFilmRating(@Param("userId") Long userId,
                              @Param("filmId") Long filmId,
                              @Param("rating") Long rating);
}
