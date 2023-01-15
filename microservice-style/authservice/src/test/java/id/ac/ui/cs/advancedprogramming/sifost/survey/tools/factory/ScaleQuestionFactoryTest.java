package id.ac.ui.cs.advancedprogramming.sifost.survey.tools.factory;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.dto.QuestionDTO;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.ScaleQuestion;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.ScaleQuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class ScaleQuestionFactoryTest {

    @Mock
    ScaleQuestionRepository scaleQuestionRepository;

    @InjectMocks
    ScaleQuestionFactory scaleQuestionFactory;

    private Survey survey;
    private QuestionDTO questionDTO;
    private ScaleQuestion question;

    @BeforeEach
    void setUp() {
        survey = new Survey("Title");
        questionDTO = new QuestionDTO();
        question = new ScaleQuestion(survey, "Title");
    }

    @Test
    void testCreateAndSaveQuestion() {
        lenient().when(scaleQuestionRepository.save(question)).thenReturn(question);
        assertAll(() -> scaleQuestionFactory.createAndSaveQuestion(survey, questionDTO));
    }
}