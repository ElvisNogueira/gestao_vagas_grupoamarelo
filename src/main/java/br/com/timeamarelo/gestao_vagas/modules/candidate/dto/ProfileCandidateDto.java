package br.com.timeamarelo.gestao_vagas.modules.candidate.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ProfileCandidateDto {
    private String description;
    private String username;
    private UUID id;
    private String name;
    private String email;
}
