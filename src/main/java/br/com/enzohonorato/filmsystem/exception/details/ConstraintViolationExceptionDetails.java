package br.com.enzohonorato.filmsystem.exception.details;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ConstraintViolationExceptionDetails {
    private String title;
    private List<FieldMessage> fieldMessages;
}
