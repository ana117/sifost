package id.ac.ui.cs.advancedprogramming.sifost.survey.controller;

import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.InterestRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.RoleRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.UserRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.service.AuthService;
import id.ac.ui.cs.advancedprogramming.sifost.auth.service.InterestService;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.service.CreateSurveyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = CreateSurveyController.class)
class CreateSurveyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CreateSurveyService createSurveyService;

    @MockBean
    private AuthService authService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleRepository roleRepository;
    @MockBean
    private InterestRepository interestRepository;
    @MockBean
    private InterestService interestService;

    @Test
    void testGetCreateSurveyPage() throws Exception {
        mockMvc.perform(get("/survey/create"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetSuccessPage() throws Exception {
        mockMvc.perform(get("/survey/success/1"))
                .andExpect(status().isOk());
    }
}