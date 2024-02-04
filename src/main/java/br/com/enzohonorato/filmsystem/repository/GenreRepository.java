package br.com.enzohonorato.filmsystem.repository;

import br.com.enzohonorato.filmsystem.domain.genre.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Query(value = "SELECT * FROM genre ORDER BY id ASC", nativeQuery = true)
    List<Genre> findAllOrderById();
}
