package br.com.enzohonorato.filmsystem.service;

import br.com.enzohonorato.filmsystem.domain.user.User;
import br.com.enzohonorato.filmsystem.exception.BadRequestException;
import br.com.enzohonorato.filmsystem.repository.UserRepository;
import br.com.enzohonorato.filmsystem.requests.user.UserRegisterRequestDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    ModelMapper modelMapper = new ModelMapper();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userRepository.findByLogin(username);

        if (userDetails == null) throw new UsernameNotFoundException("User not found");

        return userDetails;
    }

    public User save(UserRegisterRequestDTO userRegisterRequestDTO) {
        if (this.findByLogin(userRegisterRequestDTO.getLogin()) != null)
            throw new BadRequestException("User already exists");

        String encryptedPassword = new BCryptPasswordEncoder().encode(userRegisterRequestDTO.getPassword());
        userRegisterRequestDTO.setPassword(encryptedPassword);

        User user = modelMapper.map(userRegisterRequestDTO, User.class);
        user.setRole(user.getRole().toUpperCase());

        return userRepository.save(user);
    }

    public User findByLogin(String login) {
        return (User) userRepository.findByLogin(login);
    }
}
