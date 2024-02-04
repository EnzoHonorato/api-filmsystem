package br.com.enzohonorato.filmsystem.repository;

import br.com.enzohonorato.filmsystem.domain.film.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
}
