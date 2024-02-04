package br.com.enzohonorato.filmsystem.requests.user;

import lombok.Data;

@Data
public class UserLoginRequestDTO {
    private String login;
    private String password;
}
