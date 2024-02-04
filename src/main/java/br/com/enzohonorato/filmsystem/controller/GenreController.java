package br.com.enzohonorato.filmsystem.controller;

import br.com.enzohonorato.filmsystem.domain.genre.Genre;
import br.com.enzohonorato.filmsystem.requests.genre.GenrePostRequestBody;
import br.com.enzohonorato.filmsystem.requests.genre.GenrePutRequestBody;
import br.com.enzohonorato.filmsystem.service.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/genres")
public class GenreController {
    private final GenreService genreService;

    @Operation(summary = "Lista todos os gêneros", description = "Retorna um array JSON com um objeto para cada gênero salvo na base de dados", tags = {"genres - USER"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado corretamente via JWT")
    })
    @GetMapping
    public ResponseEntity<List<Genre>> listAll() {
        return new ResponseEntity<>(genreService.listAll(), HttpStatus.OK);
    }


    @Operation(summary = "Busca gênero por Id", description = "Retorna um objeto JSON com o gênero do Id informado", tags = {"genres - USER"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Genre.class))),
            @ApiResponse(responseCode = "400", description = "Gênero com o Id informado não existe na base de dados"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado corretamente via JWT")
    })
    @GetMapping(path = "/{id}")
    public ResponseEntity<Genre> findById(@PathVariable Long id) {
        return new ResponseEntity<>(genreService.findById(id), HttpStatus.OK);
    }


    @Operation(summary = "Salva um gênero", description = "Recebe no corpo da requisição um objeto JSON contendo os atributos do gênero a ser salvo", tags = {"genres - ADMIN"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Genre.class))),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos atributos"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado corretamente via JWT")
    })
    @PostMapping
    public ResponseEntity<Genre> save(@RequestBody GenrePostRequestBody genrePostRequestBody) {
        return new ResponseEntity<>(genreService.save(genrePostRequestBody), HttpStatus.CREATED);
    }


    @Operation(summary = "Deleta um gênero por Id", description = "Deleta o gênero que possui como Id o Id informado", tags = {"genres - ADMIN"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Gênero com o Id informado não existe na base de dados"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado corretamente via JWT")
    })
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        genreService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(summary = "Atualiza um gênero", description = "Recebe no corpo da requisição um objeto JSON contendo os atributos do gênero a ser atualizado", tags = {"genres - ADMIN"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos atributos"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado corretamente via JWT")
    })
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody GenrePutRequestBody genrePutRequestBody) {
        genreService.update(genrePutRequestBody);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
