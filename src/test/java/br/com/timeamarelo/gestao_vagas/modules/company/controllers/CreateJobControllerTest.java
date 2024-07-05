package br.com.timeamarelo.gestao_vagas.modules.company.controllers;

import br.com.timeamarelo.gestao_vagas.modules.company.dto.CreateJobDto;
import br.com.timeamarelo.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.timeamarelo.gestao_vagas.modules.company.repositories.CompanyRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static br.com.timeamarelo.gestao_vagas.modules.utils.TestUtils.generateToken;
import static br.com.timeamarelo.gestao_vagas.modules.utils.TestUtils.objectToJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @Autowired
    private CompanyRepository companyRepository;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @DisplayName("Should Create a new job")
    public void shouldCreateNewJob() throws Exception {
        var company = CompanyEntity.builder()
                .description("COMPANY_DESCRIPTION")
                .email("email@company.com")
                .password("1234567890")
                .username("COMPANY_USERNAME")
                .name("COMPANY_NAME").build();
        company = companyRepository.saveAndFlush(company);

        var createdJobDTO = CreateJobDto.builder()
                .benefits("BENEFITS_TEST")
                .description("DESCRIPTION_TEST")
                .level("LEVEL_TEST")
                .build();

        mockMvc.perform(
                post("/company/job/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(createdJobDTO))
                        .header(
                                "Authorization",
                                generateToken(company.getId(), "JAVAGAS_@123#"))
        ).andExpect(status().isOk());
    }

    @Test
    public void shouldNotBeAbleCreateNewJobIfCompanyNotFound() throws Exception {
        var createdJobDTO = CreateJobDto.builder()
                .benefits("BENEFITS_TEST")
                .description("DESCRIPTION_TEST")
                .level("LEVEL_TEST")
                .build();

        mockMvc.perform(
                post("/company/job/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(
                                "Authorization",
                                generateToken(UUID.randomUUID(), "JAVAGAS_@123#")
                        ).content(objectToJson(createdJobDTO))
        ).andExpect(status().isUnprocessableEntity());
    }
}
