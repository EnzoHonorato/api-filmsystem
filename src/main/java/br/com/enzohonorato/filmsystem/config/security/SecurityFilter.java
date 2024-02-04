package br.com.enzohonorato.filmsystem.config.security;

import br.com.enzohonorato.filmsystem.repository.UserRepository;
import br.com.enzohonorato.filmsystem.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UserRepository userRepository;

    // Este filtro é executado mesmo antes dos endpoints permitAll()
    // Mas como o permitAll() não precisa de ROLE de usuário nenhum, ele nem olha se o usuário no contexto tem permissão
    // Então tanto faz se a requisição para um endpoint permitAll() veio sem ou com token errado
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = this.recoverToken(request);

        if (token != null) {
            String login = tokenService.validateToken(token);

            if (!login.equals("")) {
                UserDetails user = userRepository.findByLogin(login);
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader("Authorization");
        if (header == null) return null;
        else return header.replace("Bearer ", "");
    }
}
