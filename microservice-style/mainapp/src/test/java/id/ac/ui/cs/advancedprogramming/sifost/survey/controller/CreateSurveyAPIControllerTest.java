package id.ac.ui.cs.advancedprogramming.sifost.survey.controller;

import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.Interest;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.InterestRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.RoleRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.UserRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.service.AuthService;
import id.ac.ui.cs.advancedprogramming.sifost.auth.service.InterestService;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.dto.SurveyDTO;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.QuestionTypes;
import id.ac.ui.cs.advancedprogramming.sifost.survey.service.CreateSurveyService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = CreateSurveyAPIController.class)
class CreateSurveyAPIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateSurveyService createSurveyService;

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
    private Interest interest;

    @BeforeEach
    void setUp() {
        survey = new Survey("Survey Title");
        survey.setSurveyId(1);
        interest = new Interest("Olahraga");
        interestRepository.save(interest);
    }

    @Test
    void testGetQuestionTypes() throws Exception {
        List<QuestionTypes> mockTypes = new ArrayList<>();
        mockTypes.add(null);
        mockTypes.add(null);
        mockTypes.add(null);
        Mockito.when(createSurveyService.getQuestionTypes()).thenReturn(mockTypes);

        mockMvc.perform(get("/survey/api/get-question-types")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void testSubmitForm() throws Exception {
        when(createSurveyService.createSurvey(any(SurveyDTO.class))).thenReturn(survey);

        JSONObject surveyJson = new JSONObject();
        surveyJson.put("title",survey.getTitle());
        surveyJson.put("interest", interest.getName());
        String jsonSurvey = surveyJson.toString();

        mockMvc.perform(post("/survey/api/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonSurvey))
                .andExpect(status().isOk());
    }
}
