package br.com.enzohonorato.filmsystem.exception.details;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FieldMessage {
    private String field;
    private String message;
}
