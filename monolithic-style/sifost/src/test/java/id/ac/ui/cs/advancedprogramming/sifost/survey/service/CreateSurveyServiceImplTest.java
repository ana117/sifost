package id.ac.ui.cs.advancedprogramming.sifost.survey.service;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.dto.QuestionDTO;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.dto.SurveyDTO;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.QuestionTypes;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.SimpleQuestion;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.SurveyRepository;
import id.ac.ui.cs.advancedprogramming.sifost.survey.tools.QuestionMaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateSurveyServiceImplTest {

    @Mock
    private SurveyRepository surveyRepository;

    @Mock
    private QuestionMaker questionMaker;

    @InjectMocks
    private final CreateSurveyService createSurveyService = new CreateSurveyServiceImpl();

    private SurveyDTO surveyDTO;
    private Survey titleOnlySurvey;
    private Survey survey;

    @BeforeEach
    void setUp() {
        titleOnlySurvey = new Survey("Survey Title");

        survey = new Survey("Survey Title");
        SimpleQuestion simpleQuestion = new SimpleQuestion(survey, "A Question");
        survey.addQuestion(simpleQuestion);
        survey.setSurveyId(1);

        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setPrompt("A Question");
        List<QuestionDTO> questions = new ArrayList<>();
        questions.add(questionDTO);

        surveyDTO = new SurveyDTO();
        surveyDTO.setTitle("Survey Title");
        surveyDTO.setQuestions(questions);
    }

    @Test
    void testGetQuestionTypes() {
        List<QuestionTypes> expected = List.of(QuestionTypes.values());
        List<QuestionTypes> result = createSurveyService.getQuestionTypes();
        assertEquals(3, result.size());
        assertIterableEquals(expected, result);
    }

    @Test
    void testCreateSurvey() {
        lenient().when(surveyRepository.save(titleOnlySurvey)).thenReturn(titleOnlySurvey);

        Survey result = createSurveyService.createSurvey(surveyDTO);
        assertEquals(survey.getTitle(), result.getTitle());
        assertEquals(1, survey.getSimpleQuestions().size());
    }

    @Test
    void testGetSurveyById() {
        when(surveyRepository.getById(1L)).thenReturn(survey);

        Survey result = createSurveyService.getSurveyById(1L);
        assertEquals(survey.getSurveyId(), result.getSurveyId());
        assertEquals(survey.getTitle(), result.getTitle());
        assertEquals(survey.getQuestions(), result.getQuestions());
    }
}