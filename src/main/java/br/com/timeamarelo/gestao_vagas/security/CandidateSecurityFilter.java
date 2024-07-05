package br.com.timeamarelo.gestao_vagas.security;

import br.com.timeamarelo.gestao_vagas.providers.CandidateJWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class CandidateSecurityFilter extends OncePerRequestFilter {

    @Autowired
    private CandidateJWTProvider candidateJWTProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().startsWith("/candidate")) {
            SecurityContextHolder.getContext().setAuthentication(null);
            var header = request.getHeader("Authorization");

            if (header != null) {
                var token = this.candidateJWTProvider.validateToken(header);

                if (token == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                var subject = token.getSubject();
                request.setAttribute("candidate_id", subject);

                var roles = token.getClaim("roles").asList(String.class);
                var grants = roles.stream()
                        .map(role ->
                                new SimpleGrantedAuthority("ROLE_" + role)
                        ).toList();

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(subject, null, grants);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }
}
