package br.com.enzohonorato.filmsystem.requests.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginResponseDTO {
    private String token;
    private String login;
    private String role;
}
