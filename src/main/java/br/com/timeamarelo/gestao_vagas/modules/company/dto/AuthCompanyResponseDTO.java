package br.com.timeamarelo.gestao_vagas.modules.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AuthCompanyResponseDTO {

    private String token;
    private Long expire_in;

}
