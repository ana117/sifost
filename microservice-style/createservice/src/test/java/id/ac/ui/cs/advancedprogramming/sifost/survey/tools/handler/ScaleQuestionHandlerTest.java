package id.ac.ui.cs.advancedprogramming.sifost.survey.tools.handler;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.ScaleQuestion;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.ScaleQuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class ScaleQuestionHandlerTest {

    @Mock
    ScaleQuestionRepository scaleQuestionRepository;
    @InjectMocks
    private ScaleQuestionHandler scaleQuestionHandler = new ScaleQuestionHandler();
    @Mock
    SimpleQuestionHandler simpleQuestionHandler = new SimpleQuestionHandler();
    Survey survey;
    List<String> answers;
    @BeforeEach
    public void setUp(){
        survey = new Survey("Survey Title");

        ScaleQuestion scaleQuestion = new ScaleQuestion(survey, "A Question");
        List<Integer> temp3 = new ArrayList<>();
        scaleQuestion.setAnswers(temp3);
        survey.addQuestion(scaleQuestion);
        survey.setSurveyId(1);
        answers = new ArrayList<>();
        answers.add("0");

    }

    @Test
    void testHandle2(){

        scaleQuestionHandler.setNextHandler(simpleQuestionHandler);
        Survey temp = scaleQuestionHandler.handle(answers,survey);
        assertNull(temp);
    }
}
