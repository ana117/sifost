package id.ac.ui.cs.advancedprogramming.sifost.survey.controller;

import id.ac.ui.cs.advancedprogramming.sifost.auth.AuthInitializer;
import id.ac.ui.cs.advancedprogramming.sifost.auth.CustomUserDetails;
import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.User;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.InterestRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.RoleRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.UserRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.service.AuthService;
import id.ac.ui.cs.advancedprogramming.sifost.auth.service.CustomUserDetailsServiceImpl;
import id.ac.ui.cs.advancedprogramming.sifost.auth.service.InterestService;
import id.ac.ui.cs.advancedprogramming.sifost.survey.service.ForbiddenDeletionException;
import id.ac.ui.cs.advancedprogramming.sifost.survey.service.RDSurveyService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityNotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        classes = SurveyControllerTestConfig.class,
        properties = "spring.main.allow-bean-definition-overriding=true"
)
@AutoConfigureMockMvc(addFilters = false)
class SurveyAPIControllerTest {

    @MockBean
    RDSurveyService rdSurveyService;

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
    @MockBean
    private AuthInitializer authInitializer;

    @Autowired
    User testUser;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails("username")
    void testDeleteSurveyById() throws Exception {
        doNothing().when(rdSurveyService).deleteSurveyById(any(Long.class), any(User.class));
        mockMvc.perform(get("/survey/api/delete/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("username")
    void testDeleteNonExistentSurveyById() throws Exception {
        doThrow(EntityNotFoundException.class).when(rdSurveyService).deleteSurveyById(any(Long.class), any(User.class));
        mockMvc.perform(get("/survey/api/delete/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails("username")
    void testDeleteOthersSurveyById() throws Exception {
        doThrow(ForbiddenDeletionException.class).when(rdSurveyService).deleteSurveyById(any(Long.class), any(User.class));
        mockMvc.perform(get("/survey/api/delete/1"))
                .andExpect(status().isForbidden());
    }
}