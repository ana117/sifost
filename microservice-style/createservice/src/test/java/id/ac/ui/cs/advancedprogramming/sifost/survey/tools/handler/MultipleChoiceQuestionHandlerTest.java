package id.ac.ui.cs.advancedprogramming.sifost.survey.tools.handler;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.MultipleChoiceQuestion;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.SimpleQuestion;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.MultipleChoiceQuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class MultipleChoiceQuestionHandlerTest {

    @Mock
    MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;
    @InjectMocks
    private MultipleChoiceQuestionHandler multipleChoiceQuestionHandler = new MultipleChoiceQuestionHandler();
    @Mock
    ScaleQuestionHandler scaleQuestionHandler = new ScaleQuestionHandler();
    Survey survey;
    List<String> answers;
    @BeforeEach
    public void setUp(){
        survey = new Survey("Survey Title");
        List<String> ans = new ArrayList();
        ans.add("A");
        ans.add("B");
        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion(survey, "A Question",ans);
        List<Integer> temp3 = new ArrayList<>();
        multipleChoiceQuestion.setAnswers(temp3);
        survey.addQuestion(multipleChoiceQuestion);
        survey.setSurveyId(1);
        answers = new ArrayList<>();
        answers.add("0#0");
        answers.add("&&&&&");
    }


    @Test
    void testHandle2(){

        multipleChoiceQuestionHandler.setNextHandler(scaleQuestionHandler);
        Survey temp = multipleChoiceQuestionHandler.handle(answers,survey);
        assertEquals(null,temp);
    }
}
