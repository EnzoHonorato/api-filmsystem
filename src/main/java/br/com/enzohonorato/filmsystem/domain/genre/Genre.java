package br.com.enzohonorato.filmsystem.domain.genre;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "The field 'name' cannot be blank")
    @Column(nullable = false, length = 50, unique = true)
    private String name;
}
