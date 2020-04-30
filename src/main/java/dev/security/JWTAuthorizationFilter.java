package dev.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Filtre permettant de passer du jeton JWT à un utilisateur connecté au sens Spring Security.
 */
@Configuration
public class JWTAuthorizationFilter  extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    @Value("${jwt.cookie}")
    private String TOKEN_COOKIE;

    @Value("${jwt.secret}")
    private String SECRET;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        // Recherche du jeton par Cookie
        if(req.getCookies() != null) {
            Stream.of(req.getCookies()).filter(cookie -> cookie.getName().equals(TOKEN_COOKIE))
                    .map(Cookie::getValue)
                    .forEach(token -> {

                        try {
                            Claims body = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();

                            String username = body.getSubject();

                            List<SimpleGrantedAuthority> roles = Arrays.stream(body.get("roles", String.class).split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

                            Authentication authentication =  new UsernamePasswordAuthenticationToken(username, null, roles);
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        } catch (JwtException e) {
                            // En cas d'erreur de lecture du jeton, la requête n'est pas authentifiée et n'aura pas accès aux ressources sécurisées
                            LOGGER.error("Erreur de lecture du jeton JWT", e);
                        }
                    });
        }

        chain.doFilter(req, res);

    }

}
