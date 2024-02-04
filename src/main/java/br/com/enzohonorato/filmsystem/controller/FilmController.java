package br.com.enzohonorato.filmsystem.controller;

import br.com.enzohonorato.filmsystem.domain.film.Film;
import br.com.enzohonorato.filmsystem.requests.film.FilmPatchRequestBody;
import br.com.enzohonorato.filmsystem.requests.film.FilmPostRequestBody;
import br.com.enzohonorato.filmsystem.requests.film.FilmPutRequestBody;
import br.com.enzohonorato.filmsystem.service.FilmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @Operation(summary = "Lista todos os filmes", description = "Retorna um array JSON com um objeto para cada filme salvo na base de dados", tags = {"films - USER"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado corretamente via JWT")
    })
    @GetMapping
    public ResponseEntity<List<Film>> listAll() {
        return new ResponseEntity<>(filmService.listAll(), HttpStatus.OK);
    }


    @Operation(summary = "Busca filme por Id", description = "Retorna um objeto JSON com o filme do Id informado", tags = {"films - USER"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Film.class))),
            @ApiResponse(responseCode = "400", description = "Filme com o Id informado não existe na base de dados"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado corretamente via JWT")
    })
    @GetMapping(path = "/{id}")
    public ResponseEntity<Film> findById(@PathVariable Long id) {
        return new ResponseEntity<>(filmService.findById(id), HttpStatus.OK);
    }


    @Operation(summary = "Salva um filme", description = "Recebe no corpo da requisição um objeto JSON contendo os atributos do filme a ser salvo", tags = {"films - ADMIN"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Film.class))),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos atributos"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado corretamente via JWT")
    })
    @PostMapping
    public ResponseEntity<Film> save(@RequestBody @Valid FilmPostRequestBody filmPostRequestBody) {
        return new ResponseEntity<>(filmService.save(filmPostRequestBody), HttpStatus.CREATED);
    }


    @Operation(summary = "Deleta um filme por Id", description = "Deleta o filme que possui como Id o Id informado", tags = {"films - ADMIN"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Filme com o Id informado não existe na base de dados"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado corretamente via JWT")
    })
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        filmService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(summary = "Atualiza um filme", description = "Recebe no corpo da requisição um objeto JSON contendo os atributos do filme a ser atualizado", tags = {"films - ADMIN"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos atributos"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado corretamente via JWT")
    })
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody FilmPutRequestBody filmPutRequestBody) {
        filmService.update(filmPutRequestBody);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(summary = "Atualiza parcialmente um filme (não são necessários todos os atributos)", description = "Recebe no corpo da requisição um objeto JSON contendo os atributos do filme a ser atualizado", tags = {"films - ADMIN"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos atributos"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado corretamente via JWT")
    })
    @PatchMapping
    public ResponseEntity<Void> partiallyUpdate(@RequestBody FilmPatchRequestBody filmPatchRequestBody) {
        filmService.partiallyUpdate(filmPatchRequestBody);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
