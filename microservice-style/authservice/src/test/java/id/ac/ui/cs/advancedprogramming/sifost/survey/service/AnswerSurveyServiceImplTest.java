package id.ac.ui.cs.advancedprogramming.sifost.survey.service;

import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.SurveyRepository;
import id.ac.ui.cs.advancedprogramming.sifost.survey.tools.handler.MultipleChoiceQuestionHandler;
import id.ac.ui.cs.advancedprogramming.sifost.survey.tools.handler.ScaleQuestionHandler;
import id.ac.ui.cs.advancedprogramming.sifost.survey.tools.handler.SimpleQuestionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class AnswerSurveyServiceImplTest {
    @Mock
    MultipleChoiceQuestionHandler multipleChoiceQuestionHandler;
    @Mock
    ScaleQuestionHandler scaleQuestionHandler;
    @Mock
    SimpleQuestionHandler simpleQuestionHandler;
    @Mock
    SurveyRepository surveyRepository;
    @InjectMocks
    AnswerSurveyServiceImpl answerSurveyService = new AnswerSurveyServiceImpl();
    final String ans1 = "halo";
    @Test
    void saveAnswerTest(){
        List<String> answers = new ArrayList<>();
        answers.add(ans1);

        assertNull(answerSurveyService.saveAnswer(answers, 1L));
    }
}
