package br.com.timeamarelo.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateJobDto {
    @Schema(example = "vaga para pessoa desenvolvedora júnior")
    private String description;
    @Schema(example = "Vale refeição, plano de saúde")
    private String benefits;
    @Schema(example = "JUNIOR")
    private String level;
}
