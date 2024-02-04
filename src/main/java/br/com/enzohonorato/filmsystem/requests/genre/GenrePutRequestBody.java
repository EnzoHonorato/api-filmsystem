package br.com.enzohonorato.filmsystem.requests.genre;

import lombok.Data;

@Data
public class GenrePutRequestBody {
    private Long id;
    private String name;
}