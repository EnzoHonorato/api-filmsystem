package br.com.enzohonorato.filmsystem.domain.film;

import br.com.enzohonorato.filmsystem.domain.genre.Genre;
import br.com.enzohonorato.filmsystem.domain.userfilm.UserFilm;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "The field 'name' cannot be blank")
    @Column(nullable = false, length = 100)
    private String name;
    @NotNull(message = "The field 'minutes' cannot be null")
    @Max(value = 999, message = "The field 'minutes' must be a valid integer less than 1000")
    @Column(nullable = false)
    private Long minutes;
    @NotNull(message = "The field 'releaseDate' cannot be null and must be a date following the pattern YYYY-MM-DD'")
    @Column(nullable = false)
    private LocalDate releaseDate;

    @ManyToOne
    @NotNull(message = "The film must be associated with a genre (only the field 'id' of the genre is mandatory)")
    @JoinColumn(name = "fk_genre", nullable = false, columnDefinition = "bigint(20) default 1")
    private Genre genre;

//    @OneToMany(mappedBy = "film")
//    private List<UserFilm> userFilmList;
}
