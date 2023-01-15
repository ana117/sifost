package id.ac.ui.cs.advancedprogramming.sifost.survey.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advancedprogramming.sifost.SifostApplication;
import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.Interest;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.dto.SurveyDTO;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.QuestionTypes;
import id.ac.ui.cs.advancedprogramming.sifost.survey.service.ForbiddenDeletionException;
import id.ac.ui.cs.advancedprogramming.sifost.survey.service.SurveyAPIService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SurveyAPIController.class)
class SurveyAPIControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SurveyAPIService surveyAPIService;
    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testGetAllInterestsName() throws Exception {
        List<String> interestList = new ArrayList<>();
        interestList.add("a");

        when(surveyAPIService.getAllInterestsName()).thenReturn(interestList);
        mockMvc.perform(get("/api/interests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void testGetSurveyById() throws Exception {
        Survey survey = new Survey();
        survey.setTitle("Title");

        when(surveyAPIService.getSurveyById(1L)).thenReturn(Optional.of(survey));
        mockMvc.perform(get("/api/survey/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", Matchers.equalTo("Title")));
    }

    @Test
    void testGetInvalidSurveyById() throws Exception {
        Optional<Survey> invalidSurvey = Optional.empty();

        when(surveyAPIService.getSurveyById(1L)).thenReturn(invalidSurvey);
        mockMvc.perform(get("/api/survey/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetSurveyListByOwner() throws Exception {
        List<Survey> surveyList = new ArrayList<>();
        Survey survey = new Survey();
        survey.setTitle("Title");
        surveyList.add(survey);

        when(surveyAPIService.getSurveysByOwner(1L)).thenReturn(surveyList);
        mockMvc.perform(get("/api/survey?ownerId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void testGetQuestionTypes() throws Exception {
        List<QuestionTypes> questionTypesList = new ArrayList<>();
        questionTypesList.add(null);

        when(surveyAPIService.getQuestionTypes()).thenReturn(questionTypesList);
        mockMvc.perform(get("/api/question-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void testSubmitForm() throws Exception {
        Survey survey = new Survey();
        survey.setSurveyId(1);
        survey.setTitle("Title");

        SurveyDTO dto = new SurveyDTO();
        dto.setTitle("Title");

        String json = mapper.writeValueAsString(dto);

        when(surveyAPIService.createSurvey(dto)).thenReturn(survey);
        mockMvc.perform(post("/api/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.equalTo(1)));
    }

    @Test
    void testDeleteSurvey() throws Exception {
        doNothing().when(surveyAPIService).deleteSurveyById(1L, 1L);
        mockMvc.perform(get("/api/delete?surveyId=1&ownerId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.equalTo("deleted")));
    }

    @Test
    void testDeleteInvalidSurvey() throws Exception {
        doThrow(EntityNotFoundException.class).when(surveyAPIService).deleteSurveyById(1L, 1L);
        mockMvc.perform(get("/api/delete?surveyId=1&ownerId=1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", Matchers.equalTo("Survey not found")));
    }

    @Test
    void testDeleteUnauthorizedSurvey() throws Exception {
        doThrow(ForbiddenDeletionException.class).when(surveyAPIService).deleteSurveyById(1L, 1L);
        mockMvc.perform(get("/api/delete?surveyId=1&ownerId=1"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$", Matchers.equalTo("You are not authorized to delete this")));
    }
}