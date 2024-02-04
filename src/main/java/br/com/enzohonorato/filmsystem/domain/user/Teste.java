package br.com.enzohonorato.filmsystem.domain.user;

import br.com.enzohonorato.filmsystem.requests.user.UserRegisterRequestDTO;
import br.com.enzohonorato.filmsystem.requests.user.UserRole;
import org.modelmapper.ModelMapper;

public class Teste {
    public static void main(String[] args) {
        UserRegisterRequestDTO userRegisterRequestDTO = new UserRegisterRequestDTO("enzo", "123", UserRole.ADMIN);
        User user = new ModelMapper().map(userRegisterRequestDTO, User.class);

        user.setRole(user.getRole().toUpperCase());
        System.out.println(user);
    }
}
