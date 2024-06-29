package uz.project.olix.config.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";

    private static final String BEARER = "Bearer ";

    private RequestAttributeSecurityContextRepository repository
            = new RequestAttributeSecurityContextRepository();

    private final JwtProvider jwtProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(AUTHORIZATION);
        if (header == null || !header.startsWith(BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }


        String token = header.substring(BEARER.length());

        if (!jwtProvider.validate(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        Claims claims = jwtProvider.parse(token);

        String email = claims.getSubject();

        List<String> roles= Arrays.asList(claims.get("roles").toString().split(","));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        roles.stream().map(SimpleGrantedAuthority::new).toList()
                )
        );
        repository.saveContext(SecurityContextHolder.getContext(), request, response);

        filterChain.doFilter(request,response);

    }


}
