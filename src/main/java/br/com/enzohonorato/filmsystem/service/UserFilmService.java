package br.com.enzohonorato.filmsystem.service;

import br.com.enzohonorato.filmsystem.domain.film.Film;
import br.com.enzohonorato.filmsystem.domain.user.User;
import br.com.enzohonorato.filmsystem.domain.userfilm.UserFilm;
import br.com.enzohonorato.filmsystem.domain.userfilm.UserFilmKey;
import br.com.enzohonorato.filmsystem.exception.BadRequestException;
import br.com.enzohonorato.filmsystem.repository.UserFilmRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor

@Service
public class UserFilmService {
    private final UserFilmRepository userFilmRepository;
    private final UserService userService;
    private final FilmService filmService;

    public List<UserFilm> listUserFilmList(Long userId) {
        return userFilmRepository.listUserFilmList(userId);
    }

    public UserFilm findById(UserFilmKey userFilmKey) {
        return userFilmRepository.findById(userFilmKey)
                .orElseThrow(() -> new BadRequestException("UserFilm association not found"));
    }

    public void associateUserFilm(String userLogin, Long filmId) {
        boolean userFilmAssociationAlreadyExists = false;

        User user = userService.findByLogin(userLogin);
        Film film = filmService.findById(filmId);

        try {
            UserFilmKey userFilmKey = new UserFilmKey(user.getId(), filmId);
            this.findById(userFilmKey);
            userFilmAssociationAlreadyExists = true;
        } catch (BadRequestException bre) {

        }

        if (userFilmAssociationAlreadyExists) throw new BadRequestException("UserFilm association already exists");

        UserFilm userFilm = new UserFilm(new UserFilmKey(user.getId(), film.getId()), user, film);
        userFilmRepository.save(userFilm);
    }

    public void disassociateUserFilm(String userLogin, Long filmId) {
        User user = userService.findByLogin(userLogin);
        Film film = filmService.findById(filmId);

        UserFilmKey userFilmKey = new UserFilmKey(user.getId(), film.getId());
        UserFilm userFilm = this.findById(userFilmKey);

        userFilmRepository.delete(userFilm);
    }

    @Transactional(rollbackOn = Exception.class)
    public void updateUserFilmRating(Long userId, Long filmId, Long rating) {
        filmService.findById(filmId);
        this.findById(new UserFilmKey(userId, filmId));

        userFilmRepository.updateUserFilmRating(userId, filmId, rating);
    }
}
