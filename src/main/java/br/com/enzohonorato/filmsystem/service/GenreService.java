package br.com.enzohonorato.filmsystem.service;

import br.com.enzohonorato.filmsystem.domain.film.Film;
import br.com.enzohonorato.filmsystem.domain.genre.Genre;
import br.com.enzohonorato.filmsystem.exception.BadRequestException;
import br.com.enzohonorato.filmsystem.repository.GenreRepository;
import br.com.enzohonorato.filmsystem.requests.film.FilmPutRequestBody;
import br.com.enzohonorato.filmsystem.requests.genre.GenrePostRequestBody;
import br.com.enzohonorato.filmsystem.requests.genre.GenrePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;
    ModelMapper modelMapper = new ModelMapper();

    public List<Genre> listAll() {
        return genreRepository.findAllOrderById();
    }

    public Genre findById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Genre not found"));
    }

    public Genre save(GenrePostRequestBody genrePostRequestBody) {
        ModelMapper modelMapper = new ModelMapper();
        Genre genreToBeSaved = modelMapper.map(genrePostRequestBody, Genre.class);

        return genreRepository.save(genreToBeSaved);
    }

    public void delete(Long id) {
        Genre genreToBeDeleted = this.findById(id);

        genreRepository.delete(genreToBeDeleted);
    }

    public void update(GenrePutRequestBody genrePutRequestBody) {
        this.findById(genrePutRequestBody.getId());

        Genre genreToBeUpdated = modelMapper.map(genrePutRequestBody, Genre.class);

        genreRepository.save(genreToBeUpdated);
    }
}
