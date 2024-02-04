package br.com.enzohonorato.filmsystem.requests.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegisterRequestDTO {
    @NotBlank(message = "The field 'login' cannot be blank")
    private String login;

    @NotBlank(message = "The field 'password' cannot be blank")
    private String password;
    private UserRole role;
}
