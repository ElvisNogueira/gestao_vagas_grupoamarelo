package br.com.timeamarelo.gestao_vagas.modules.company.controllers;

import br.com.timeamarelo.gestao_vagas.modules.company.dto.AuthCompanyRequestDTO;
import br.com.timeamarelo.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.timeamarelo.gestao_vagas.modules.company.useCases.AuthCompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class AuthCompanyController {

    @Autowired
    private AuthCompanyUseCase authCompanyUseCase;

    @PostMapping("/auth")
    @Tag(name = "Companhia")
    @Operation(
            summary = "Autenticar company",
            description = "Função que autentica uma companhia"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(
                    schema = @Schema(implementation = AuthCompanyResponseDTO.class)
            )
            ),
            @ApiResponse(
                    responseCode = "401", content = @Content(
                    examples = @ExampleObject("Username/password incorrect")
            )
            )
    })
    public ResponseEntity<Object> create(@RequestBody AuthCompanyRequestDTO authCompanyRequestDTO) {
        try {
            var result = this.authCompanyUseCase.execute(authCompanyRequestDTO);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
