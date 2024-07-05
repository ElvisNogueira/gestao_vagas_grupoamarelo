package br.com.timeamarelo.gestao_vagas.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthCandidateRequestDto {
    @Schema(example = "candidate1")
    private String username;
    @Schema(example = "1234567890")
    private String password;
}
