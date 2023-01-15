package id.ac.ui.cs.advancedprogramming.sifost.survey.controller;

import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.InterestRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.RoleRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.UserRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.service.AuthService;
import id.ac.ui.cs.advancedprogramming.sifost.auth.service.InterestService;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.SurveyRepository;
import id.ac.ui.cs.advancedprogramming.sifost.survey.service.CreateSurveyService;
import id.ac.ui.cs.advancedprogramming.sifost.survey.service.RDSurveyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = ViewSurveyController.class)
class ViewSurveyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    SurveyRepository surveyRepository;

    @MockBean
    CreateSurveyService createSurveyService;

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

    private Survey survey;

    @BeforeEach
    void setUp() {
        survey = new Survey("Title");
        survey.setSurveyId(1);
        survey.setTitle("Test");
        surveyRepository.save(survey);
    }

    @Test
    void testGetViewPage() throws Exception {
        when(createSurveyService.getSurveyById(1L)).thenReturn(survey);
        when(surveyRepository.getById(1L)).thenReturn(survey);

        mockMvc.perform(get("/survey/view/1"))
                .andExpect(status().isOk());
    }
}
