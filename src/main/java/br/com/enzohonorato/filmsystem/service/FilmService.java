package br.com.enzohonorato.filmsystem.service;

import br.com.enzohonorato.filmsystem.domain.film.Film;
import br.com.enzohonorato.filmsystem.domain.user.User;
import br.com.enzohonorato.filmsystem.exception.BadRequestException;
import br.com.enzohonorato.filmsystem.repository.FilmRepository;
import br.com.enzohonorato.filmsystem.requests.film.FilmPatchRequestBody;
import br.com.enzohonorato.filmsystem.requests.film.FilmPostRequestBody;
import br.com.enzohonorato.filmsystem.requests.film.FilmPutRequestBody;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmRepository filmRepository;
    ModelMapper modelMapper = new ModelMapper();

    public List<Film> listAll() {
        return filmRepository.findAll();
    }

    public Film findById(Long id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Film not found"));
    }

    public Film save(FilmPostRequestBody filmPostRequestBody) {
        Film filmToBeSaved = modelMapper.map(filmPostRequestBody, Film.class);

        return filmRepository.save(filmToBeSaved);
    }

    public void delete(Long id) {
        Film filmToBeDeleted = this.findById(id);

        filmRepository.delete(filmToBeDeleted);
    }

    public void update(FilmPutRequestBody filmPutRequestBody) {
        this.findById(filmPutRequestBody.getId());

        Film filmToBeUpdated = modelMapper.map(filmPutRequestBody, Film.class);

        filmRepository.save(filmToBeUpdated);
    }

    public void partiallyUpdate(FilmPatchRequestBody filmPatchRequestBody) {
        Film film = this.findById(filmPatchRequestBody.getId());

        Film filmToBePartiallyUpdated = checkFieldsToBeUpdated(filmPatchRequestBody, film);

        filmRepository.save(filmToBePartiallyUpdated);
    }

    public Film checkFieldsToBeUpdated(FilmPatchRequestBody filmPatchRequestBody, Film film) {
        if(filmPatchRequestBody.getName() != null) {
            film.setName(filmPatchRequestBody.getName());
        }
        if(filmPatchRequestBody.getMinutes() != null) {
            film.setMinutes(filmPatchRequestBody.getMinutes());
        }
        if(filmPatchRequestBody.getReleaseDate() != null) {
            film.setReleaseDate(filmPatchRequestBody.getReleaseDate());
        }
        if(filmPatchRequestBody.getGenre() != null) {
            film.setGenre(filmPatchRequestBody.getGenre());
        }

        return film;
    }



}
