package id.ac.ui.cs.advancedprogramming.sifost.survey.tools.handler;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.SimpleQuestion;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.SimpleQuestionRepository;
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
class SimpleQuestionHandlerTest {

    @Mock
    SimpleQuestionRepository simpleQuestionRepository;
    @InjectMocks
    private SimpleQuestionHandler simpleQuestionHandler = new SimpleQuestionHandler();
    @Mock
    ScaleQuestionHandler scaleQuestionHandler = new ScaleQuestionHandler();
    Survey survey;
    List<String> answers;
    @BeforeEach
    public void setUp(){
        survey = new Survey("Survey Title");

        SimpleQuestion simpleQuestion = new SimpleQuestion(survey, "A Question");
        List<String> temp3 = new ArrayList<>();
        simpleQuestion.setAnswers(temp3);
        survey.addQuestion(simpleQuestion);
        survey.setSurveyId(1);
        answers = new ArrayList<>();
        answers.add("halo");
        answers.add("&&&&&");
    }


    @Test
    void testHandle2(){

        simpleQuestionHandler.setNextHandler(scaleQuestionHandler);
        Survey temp = simpleQuestionHandler.handle(answers,survey);
        assertNull(temp);
    }
}
