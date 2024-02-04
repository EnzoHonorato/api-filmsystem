package br.com.enzohonorato.filmsystem.controller;

import br.com.enzohonorato.filmsystem.domain.user.User;
import br.com.enzohonorato.filmsystem.requests.user.UserLoginRequestDTO;
import br.com.enzohonorato.filmsystem.requests.user.UserLoginResponseDTO;
import br.com.enzohonorato.filmsystem.requests.user.UserRegisterRequestDTO;
import br.com.enzohonorato.filmsystem.service.TokenService;
import br.com.enzohonorato.filmsystem.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("auth")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;


    @Operation(summary = "Realiza o login de um usuário", description = "Recebe no corpo da requisição um objeto JSON contendo os atributos do usuário a ser logado", tags = {"auth - ALL"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso, retornando objeto JSON contendo token, login e role", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserLoginResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Credenciais incorretas")
    })
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody @Valid UserLoginRequestDTO userLoginRequestDTO) {
        Authentication credentials = new UsernamePasswordAuthenticationToken(userLoginRequestDTO.getLogin(), userLoginRequestDTO.getPassword());
        authenticationManager.authenticate(credentials);

        User user = userService.findByLogin(userLoginRequestDTO.getLogin());
        String token = tokenService.generateToken(user);

        return new ResponseEntity<>(new UserLoginResponseDTO(token, user.getLogin(), user.getRole()), HttpStatus.OK);
    }


    @Operation(summary = "Salva um usuário", description = "Recebe no corpo da requisição um objeto JSON contendo os atributos do usuário a ser salvo", tags = {"auth - ADMIN"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos atributos"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado corretamente via JWT")
    })
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequestDTO userRegisterRequestDTO) {
        User user = userService.save(userRegisterRequestDTO);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


    @Operation(summary = "Valida um usuário via token", description = "Valida com base no token utilizado na requisição", tags = {"auth - USER"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso, retornando array JSON de String contendo login e role"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado corretamente via JWT")
    })
    @GetMapping("/validate")
    public ResponseEntity<List<String>> validateToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String userLogin = user.getLogin();
        String userRole = user.getRole();

        return new ResponseEntity<>(List.of(userLogin, userRole), HttpStatus.OK);
    }
}
