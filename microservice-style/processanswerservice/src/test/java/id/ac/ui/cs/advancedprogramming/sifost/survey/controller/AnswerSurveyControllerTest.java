package id.ac.ui.cs.advancedprogramming.sifost.survey.controller;

import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.InterestRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.RoleRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.UserRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.service.AuthService;
import id.ac.ui.cs.advancedprogramming.sifost.auth.service.InterestService;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.SurveyRepository;
import id.ac.ui.cs.advancedprogramming.sifost.survey.service.AnswerSurveyService;
import id.ac.ui.cs.advancedprogramming.sifost.survey.service.CreateSurveyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AnswerSurveyController.class)
class AnswerSurveyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    SurveyRepository surveyRepository;

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
    @MockBean
    private AnswerSurveyService answerSurveyService;
    private Survey survey;

    @BeforeEach
    void setUp() {
        survey = new Survey("Title");
        survey.setSurveyId(1);
        survey.setTitle("Test");
        surveyRepository.save(survey);
    }

    @Test
    @WithMockUser
    void whenPostSaveURLShouldCallAnswerFinish() throws Exception{
        var TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        var httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        var csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
        mockMvc.perform(post("/survey/save").sessionAttr(TOKEN_ATTR_NAME,csrfToken)
                        .param(csrfToken.getParameterName(),csrfToken.getToken())
                        .param("answer","halo")
                        .param("surveyId","1"))
                .andExpect((handler().methodName("savingSurvey")));

    }

    @Test
    @WithMockUser
    void whenPostProcessURLShouldCallAnswerFinish() throws Exception{
        var TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        var httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        var csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
        mockMvc.perform(post("/survey/process").sessionAttr(TOKEN_ATTR_NAME,csrfToken)
                        .param(csrfToken.getParameterName(),csrfToken.getToken()))
                .andExpect((handler().methodName("saveSurvey")))
                .andExpect(view().name("survey/answer-finish"));

    }
    @Test
    @WithMockUser
    void whenPostProcessURLShouldCallAnswerFinish2() throws Exception{
        var TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        var httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        var csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
        when(surveyRepository.getById(1L)).thenReturn(survey);
        mockMvc.perform(post("/survey/process").sessionAttr(TOKEN_ATTR_NAME,csrfToken)
                        .param(csrfToken.getParameterName(),csrfToken.getToken())
                                .param("surveyId","1")
                                .param("answer","halo")
                                    )
                .andExpect((handler().methodName("saveSurvey")))
                .andExpect(view().name("survey/answer-finish"));

    }
    @Test
    @WithMockUser
    void whenGetSurveyIdURLShouldCallGetViewPage() throws Exception{
        when(createSurveyService.getSurveyById(1L)).thenReturn(survey);
        when(surveyRepository.getById(1L)).thenReturn(survey);
        mockMvc.perform(get("/survey/answer/1"))
                .andExpect(view().name("survey/answer-survey"));
    }

}
