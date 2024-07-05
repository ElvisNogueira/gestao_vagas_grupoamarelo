package br.com.timeamarelo.gestao_vagas.security;

import br.com.timeamarelo.gestao_vagas.providers.CompanyJWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CompanySecurityFilter extends OncePerRequestFilter {
    @Autowired
    private CompanyJWTProvider companyJwtProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        var header = request.getHeader("Authorization");

        if (request.getRequestURI().startsWith("/company")) {
            if (header != null) {
                var token = companyJwtProvider.validateToken(header);
                var subject = token.getSubject();

                if (subject.isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                var roles = token.getClaim("roles").asList(String.class);
                var grants = roles.stream()
                        .map(role ->
                                new SimpleGrantedAuthority("ROLE_" + role)
                        ).toList();
                request.setAttribute("company_id", subject);

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(subject, null, grants);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
