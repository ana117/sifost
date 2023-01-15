package id.ac.ui.cs.advancedprogramming.sifost.survey.service;

import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.Interest;
import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.Role;
import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.User;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.InterestRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.RoleRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.UserRepository;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.dto.QuestionDTO;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.dto.SurveyDTO;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.QuestionTypes;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.MultipleChoiceQuestionRepository;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.ScaleQuestionRepository;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.SimpleQuestionRepository;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.SurveyRepository;
import id.ac.ui.cs.advancedprogramming.sifost.survey.tools.QuestionMaker;
import id.ac.ui.cs.advancedprogramming.sifost.survey.tools.RoleChecker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SurveyAPIServiceImplTest {

    @Mock
    InterestRepository interestRepository;
    @Mock
    SurveyRepository surveyRepository;
    @Mock
    MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;
    @Mock
    SimpleQuestionRepository simpleQuestionRepository;
    @Mock
    ScaleQuestionRepository scaleQuestionRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    RoleRepository roleRepository;
    @Mock
    QuestionMaker questionMaker;
    @Mock
    RoleChecker roleChecker;

    @InjectMocks
    SurveyAPIService surveyAPIService = new SurveyAPIServiceImpl();

    private static final String INTEREST_NAME = "Interesting";
    private static final String SURVEY_TITLE = "Title";


    @Test
    void testGetAllInterests() {
        List<Interest> interestList = new ArrayList<>();
        Interest interest = new Interest();
        interest.setName(INTEREST_NAME);
        interestList.add(interest);

        when(interestRepository.findAll()).thenReturn(interestList);
        List<String> result = surveyAPIService.getAllInterestsName();

        assertEquals(1, result.size());
        assertEquals(INTEREST_NAME, result.get(0));
    }

    @Test
    void testGetSurveyById() {
        Survey survey = new Survey();
        survey.setTitle(SURVEY_TITLE);

        when(surveyRepository.findById(1L)).thenReturn(Optional.of(survey));
        Optional<Survey> result = surveyAPIService.getSurveyById(1L);

        assertTrue(result.isPresent());
        assertEquals(SURVEY_TITLE, result.get().getTitle());
    }

    @Test
    void testGetSurveysByOwner() {
        User user = new User();
        List<Survey> surveyList = new ArrayList<>();
        Survey survey = new Survey();
        survey.setTitle(SURVEY_TITLE);
        surveyList.add(survey);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(surveyRepository.findByOwner(user)).thenReturn(surveyList);
        when(roleChecker.isAdmin(user)).thenReturn(Boolean.FALSE);
        List<Survey> result = surveyAPIService.getSurveysByOwner(1L);

        assertEquals(surveyList.size(), result.size());
        assertEquals(SURVEY_TITLE, result.get(0).getTitle());
        verify(surveyRepository).findByOwner(user);
    }

    @Test
    void testGetSurveysByUnknownOwner() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        List<Survey> result = surveyAPIService.getSurveysByOwner(1L);

        assertEquals(0, result.size());
    }

    @Test
    void testGetSurveysByAdmin() {
        User user = new User();
        List<Survey> surveyList = new ArrayList<>();
        Survey survey = new Survey();
        survey.setTitle(SURVEY_TITLE);
        surveyList.add(survey);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(surveyRepository.findAll()).thenReturn(surveyList);
        when(roleChecker.isAdmin(user)).thenReturn(Boolean.TRUE);
        List<Survey> result = surveyAPIService.getSurveysByOwner(1L);

        assertEquals(surveyList.size(), result.size());
        assertEquals(SURVEY_TITLE, result.get(0).getTitle());
        verify(surveyRepository).findAll();
    }

    @Test
    void testGetQuestionTypes() {
        List<QuestionTypes> result = surveyAPIService.getQuestionTypes();
        assertIterableEquals(List.of(QuestionTypes.values()), result);
    }

    @Test
    void testCreateSurvey() {
        SurveyDTO dto = new SurveyDTO();
        List<QuestionDTO> questions = new ArrayList<>();
        QuestionDTO questionDTO = new QuestionDTO();
        questions.add(questionDTO);

        dto.setTitle(SURVEY_TITLE);
        dto.setQuestions(questions);
        dto.setInterest(INTEREST_NAME);

        Interest interest = new Interest();
        interest.setName(INTEREST_NAME);
        Survey survey = new Survey();
        survey.setTitle(SURVEY_TITLE);
        survey.setInterest(interest);

        when(interestRepository.getInterestByName(INTEREST_NAME)).thenReturn(interest);
        when(userRepository.getById(anyLong())).thenReturn(null);
        when(surveyRepository.save(survey)).thenReturn(survey);
        doNothing().when(questionMaker).createAndSaveQuestion(survey, questionDTO);

        Survey result = surveyAPIService.createSurvey(dto);
        assertEquals(SURVEY_TITLE, result.getTitle());
    }

    @Test
    void testTriggerNotification() {
        Survey survey = new Survey();
        assertDoesNotThrow(() -> surveyAPIService.triggerNotification(survey));
    }

    @Test
    void testDeleteSurveyById() {
        User deleter = new User();
        deleter.setUsername("A");
        deleter.setId(1L);

        Survey survey = new Survey();
        survey.setOwner(deleter);

        Role adminRole = new Role();
        List<Role> roles = new ArrayList<>();
        roles.add(adminRole);

        when(userRepository.getById(1L)).thenReturn(deleter);
        when(surveyRepository.getById(1L)).thenReturn(survey);
        when(roleRepository.findByName("ADMIN")).thenReturn(roles);
        doNothing().when(surveyRepository).delete(survey);

        assertDoesNotThrow(() -> surveyAPIService.deleteSurveyById(1L, 1L));
    }
}