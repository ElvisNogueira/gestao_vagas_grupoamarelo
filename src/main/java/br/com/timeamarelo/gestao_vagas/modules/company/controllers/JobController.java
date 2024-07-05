package br.com.timeamarelo.gestao_vagas.modules.company.controllers;

import br.com.timeamarelo.gestao_vagas.modules.company.dto.CreateJobDto;
import br.com.timeamarelo.gestao_vagas.modules.company.entities.JobEntity;
import br.com.timeamarelo.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/company/job")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    @PreAuthorize("hasRole('COMPANY')")
    @Tag(name = "Vagas")
    @Operation(
            summary = "Cadastrar uma nova vaga na plataforma",
            description = "Essa função faz o cadastro de nova vaga ofertada por uma companhia"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(
                    schema = @Schema(implementation = JobEntity.class)
            ))
    })
    @SecurityRequirement(name = "jwt_auth")
    public JobEntity create(@Valid @RequestBody CreateJobDto createJobDto, HttpServletRequest request) {
        var companyId = request.getAttribute("company_id").toString();
        var entity = JobEntity.builder()
                .description(createJobDto.getDescription())
                .level(createJobDto.getLevel())
                .benefits(createJobDto.getBenefits())
                .companyId(UUID.fromString(companyId))
                .build();

        return this.createJobUseCase.execute(entity);
    }
}
