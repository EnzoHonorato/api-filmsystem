package br.com.enzohonorato.filmsystem.controller;

import br.com.enzohonorato.filmsystem.domain.user.User;
import br.com.enzohonorato.filmsystem.domain.userfilm.UserFilm;
import br.com.enzohonorato.filmsystem.requests.userfilm.UserFilmListResponseDTO;
import br.com.enzohonorato.filmsystem.service.UserFilmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("userfilm")
public class UserFilmController {
    private final UserFilmService userFilmService;


    @Operation(summary = "Lista todos os filmes da lista do usuário que faz a requisição", description = "Retorna um objeto JSON contendo Id da relação do usuário com o filme, o filme e o rating", tags = {"userfilm - USER"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado corretamente via JWT")
    })
    @GetMapping
//    public ResponseEntity<List<UserFilmListResponseDTO>> listUserFilmList() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getPrincipal();
//        Long userId = user.getId();
//
//        List<UserFilm> userFilmList = userFilmService.listUserFilmList(userId);
//
//        List<UserFilmListResponseDTO> list = new ArrayList<>();
//        for (UserFilm userFilm : userFilmList) {
//            list.add(UserFilmListResponseDTO.builder()
//                    .id(userFilm.getId())
//                    .film(userFilm.getFilm())
//                    .rating(userFilm.getRating()).build());
//        }
//
//        return new ResponseEntity<>(list, HttpStatus.OK);
//    }

    public ResponseEntity<List<UserFilmListResponseDTO>> listUserFilmList(@AuthenticationPrincipal User user) {
        Long userId = user.getId();

        List<UserFilm> userFilmList = userFilmService.listUserFilmList(userId);

        List<UserFilmListResponseDTO> list = new ArrayList<>();
        for (UserFilm userFilm : userFilmList) {
            list.add(UserFilmListResponseDTO.builder()
                    .id(userFilm.getId())
                    .film(userFilm.getFilm())
                    .rating(userFilm.getRating()).build());
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @Operation(summary = "Relaciona o usuário que faz a requisição com o filme de Id passado", description = "Relaciona o usuário que faz a requisição com o filme de Id passado", tags = {"userfilm - USER"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Usuário já possui relação com o filme de Id informado ou  o filme não existe"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado corretamente via JWT")
    })
    @PostMapping(path = "/{filmId}")
    public ResponseEntity<Void> associateUserFilm(@PathVariable Long filmId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String userLogin = user.getLogin();

        userFilmService.associateUserFilm(userLogin, filmId);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Operation(summary = "Deleta a relação do usuário que faz a requisição com o filme de Id passado", description = "Deleta a relação do usuário que faz a requisição com o filme de Id passado", tags = {"userfilm - USER"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Usuário não possui relação com o filme com o Id informado ou o filme não existe"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado corretamente via JWT")
    })
    @DeleteMapping(path = "/{filmId}")
    public ResponseEntity<Void> disassociateUserFilm(@PathVariable Long filmId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String userLogin = user.getLogin();

        userFilmService.disassociateUserFilm(userLogin, filmId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(summary = "Atualiza o rating (0-10) da relação do usuário que faz a requisição com o filme de Id passado", description = "Atualiza o rating (0-10) da relação do usuário que faz a requisição com o filme de Id passado", tags = {"userfilm - USER"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na atualização do rating. Possíveis causas: rating fora do range (0-10), usuário não possui relação com o filme, filme não existe"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado corretamente via JWT")
    })
    @PutMapping(path = "/{filmId}/{rating}")
    public ResponseEntity<Void> updateUserFilmRating(@PathVariable Long filmId,
                                                     @PathVariable @Valid @Min(0) @Max(10) Long rating) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();

        userFilmService.updateUserFilmRating(userId, filmId, rating);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
