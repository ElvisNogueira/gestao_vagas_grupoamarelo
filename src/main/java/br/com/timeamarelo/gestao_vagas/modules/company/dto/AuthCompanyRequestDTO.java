package br.com.timeamarelo.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthCompanyRequestDTO {
    @Schema(example = "1234567890")
    private String password;
    @Schema(example = "meli")
    private String username;

}
