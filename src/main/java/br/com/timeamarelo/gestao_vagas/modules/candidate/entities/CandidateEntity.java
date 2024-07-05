package br.com.timeamarelo.gestao_vagas.modules.candidate.entities;

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

@Data
@Entity(name = "candidate")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Schema(example = "Candidate 1")
    private String name;

    @NotBlank
    @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaço.")
    @Schema(example = "candidate1")
    private String username;


    @Email(message = "O campo [email] deve conter um e-mail válido.")
    private String email;

    @Length(min=10, max=100, message = "A senha deve conter entre 10 e 100 caracteres.")
    @Schema(example = "1234567890")
    private String password;
    @Schema(example = "Pessoa desenvolvedora nível sênior")
    private String description;
    @Schema(example = "2020-atualmente: Mercado Livre")
    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createAt;

}
