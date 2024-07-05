package br.com.timeamarelo.gestao_vagas.modules.candidate.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthCandidateResponseDto {
    private String token;
    private Long expires_in;
}
