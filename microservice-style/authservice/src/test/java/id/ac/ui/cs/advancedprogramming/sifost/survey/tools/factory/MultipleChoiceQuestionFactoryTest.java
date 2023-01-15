package id.ac.ui.cs.advancedprogramming.sifost.survey.tools.factory;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.dto.QuestionDTO;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.MultipleChoiceQuestion;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.MultipleChoiceQuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import java.util.*;
@ExtendWith(MockitoExtension.class)
class MultipleChoiceQuestionFactoryTest {

    @Mock
    MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;

    @InjectMocks
    MultipleChoiceQuestionFactory multipleChoiceQuestionFactory;

    private Survey survey;
    private QuestionDTO questionDTO;
    private MultipleChoiceQuestion question;

    @BeforeEach
    void setUp() {
        survey = new Survey("Title");
        questionDTO = new QuestionDTO();
        List<String> testAns = new ArrayList<>();
        testAns.add("a");
        question = new MultipleChoiceQuestion(survey, "Title", testAns);
    }

    @Test
    void testCreateAndSaveQuestion() {
        lenient().when(multipleChoiceQuestionRepository.save(question)).thenReturn(question);
        assertAll(() -> multipleChoiceQuestionFactory.createAndSaveQuestion(survey, questionDTO));
    }
}
