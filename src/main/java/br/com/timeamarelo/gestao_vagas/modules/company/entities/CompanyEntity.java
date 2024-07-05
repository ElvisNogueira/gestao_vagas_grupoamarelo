package br.com.timeamarelo.gestao_vagas.modules.company.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "company")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaço.")
    @Schema(example = "meli")
    private String username;

    @Email(message = "O campo [email] deve conter um e-mail válido.")
    @Schema(example = "meli@mercadolivre.com")
    private String email;

    @Length(min = 10, max = 100, message = "A senha deve conter entre 10 e 100 caracteres.")
    @Schema(example = "1234567890")
    private String password;
    @Schema(example = "www.mercadolivre.com.br")
    private String website;
    @Schema(example = "Mercado Livre")
    private String name;
    @Schema(example = "Plataforma de vendas online")
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
