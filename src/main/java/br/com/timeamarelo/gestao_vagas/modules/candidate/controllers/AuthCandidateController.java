package br.com.timeamarelo.gestao_vagas.modules.candidate.controllers;

import br.com.timeamarelo.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDto;
import br.com.timeamarelo.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDto;
import br.com.timeamarelo.gestao_vagas.modules.candidate.useCases.AuthCandidateUseCase;
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
@RequestMapping("/candidate")
public class AuthCandidateController {

    @Autowired
    private AuthCandidateUseCase authCandidateUseCase;

    @PostMapping("/auth")
    @Tag(name = "Candidato")
    @Operation(
            summary = "Autenticar candidato",
            description = "Função que autentica um usuário"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", content = @Content(
                    schema = @Schema(implementation = AuthCandidateResponseDto.class)
            )
            ),
            @ApiResponse(
                    responseCode = "401", content = @Content(
                    examples = @ExampleObject("Username/password incorrect")
            )
            )
    })
    public ResponseEntity<Object> auth(@RequestBody AuthCandidateRequestDto authCandidateRequestDto) {
        try {
            var token = authCandidateUseCase.execute(authCandidateRequestDto);
            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
