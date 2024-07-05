package br.com.timeamarelo.gestao_vagas.modules.company.controllers;

import br.com.timeamarelo.gestao_vagas.modules.company.entities.CompanyEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static br.com.timeamarelo.gestao_vagas.modules.utils.TestUtils.objectToJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CreateCompanyControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @DisplayName("Should be able create new Company")
    public void shouldBeAbleCreateNewCompany() throws Exception {
        var company = CompanyEntity.builder()
                .email("teste@teste.com")
                .name("Company Test")
                .description("TEST TEST")
                .password("0123456789")
                .username("test")
                .website("www.test.com")
                .build();

        performRequestToCreateNewCompany(company)
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotBeAbleCreateNewCompanyWhenAlreadyExistsCompanyWithSameUsername() throws Exception {
        var company = CompanyEntity.builder()
                .email("teste@teste.com")
                .name("Company Test")
                .description("TEST TEST")
                .password("0123456789")
                .username("test")
                .website("www.test.com")
                .build();
        var company1 = CompanyEntity.builder()
                .email("teste1@teste.com")
                .name("Company Test 1")
                .description("TEST TEST 1")
                .password("0123456789")
                .username("test")
                .website("www.test1.com")
                .build();

        performRequestToCreateNewCompany(company);
        performRequestToCreateNewCompany(company1)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotBeAbleCreateNewCompanyWhenAlreadyExistsCompanyWithSameEmail() throws Exception {
        var company = CompanyEntity.builder()
                .email("teste@teste.com")
                .name("Company Test")
                .description("TEST TEST")
                .password("0123456789")
                .username("test")
                .website("www.test.com")
                .build();
        var company1 = CompanyEntity.builder()
                .email("teste@teste.com")
                .name("Company Test 1")
                .description("TEST TEST 1")
                .password("0123456789")
                .username("test 1")
                .website("www.test1.com")
                .build();

        performRequestToCreateNewCompany(company);
        performRequestToCreateNewCompany(company1)
                .andExpect(status().isBadRequest());
    }


    private ResultActions performRequestToCreateNewCompany(CompanyEntity company) throws Exception {
        return mockMvc.perform(
                post("/company/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(company))
        );
    }
}
