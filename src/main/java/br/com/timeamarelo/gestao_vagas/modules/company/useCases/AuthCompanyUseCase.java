package br.com.timeamarelo.gestao_vagas.modules.company.useCases;

import br.com.timeamarelo.gestao_vagas.modules.company.dto.AuthCompanyRequestDTO;
import br.com.timeamarelo.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.timeamarelo.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.timeamarelo.gestao_vagas.providers.CompanyJWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CompanyJWTProvider companyJwtProvider;

    public AuthCompanyResponseDTO execute(AuthCompanyRequestDTO authCompanyRequestDTO) {

        var company = this.companyRepository
                .findByUsername(authCompanyRequestDTO.getUsername())
                .orElseThrow(
                        () -> {
                            throw new UsernameNotFoundException("Username/Password incorrect.");
                        });

        // Verificar se a senha é igual a senha do banco de dados,
        var passwordMatches = this.passwordEncoder
                .matches(
                        authCompanyRequestDTO.getPassword(),
                        company.getPassword()
                );

        // se nao for igual, retorna erro
        if (!passwordMatches) {
            throw new ArithmeticException();

        }
        // se for igual, gerar o token

        var token = companyJwtProvider.createToken(company);
        var expire_in = companyJwtProvider.validateToken(token)
                .getExpiresAt()
                .toInstant()
                .getEpochSecond();

        return AuthCompanyResponseDTO.builder()
                .token(token)
                .expire_in(expire_in)
                .build();
    }

}
