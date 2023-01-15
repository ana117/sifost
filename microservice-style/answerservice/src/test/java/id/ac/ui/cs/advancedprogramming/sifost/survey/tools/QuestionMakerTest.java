package id.ac.ui.cs.advancedprogramming.sifost.survey.tools;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.dto.QuestionDTO;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.QuestionTypes;
import id.ac.ui.cs.advancedprogramming.sifost.survey.tools.factory.MultipleChoiceQuestionFactory;
import id.ac.ui.cs.advancedprogramming.sifost.survey.tools.factory.ScaleQuestionFactory;
import id.ac.ui.cs.advancedprogramming.sifost.survey.tools.factory.SimpleQuestionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionMakerTest {

    @Mock
    private SimpleQuestionFactory simpleQuestionFactory;

    @Mock
    private MultipleChoiceQuestionFactory multipleChoiceQuestionFactory;

    @Mock
    private ScaleQuestionFactory scaleQuestionFactory;

    @InjectMocks
    private QuestionMaker questionMaker;

    private Survey survey;
    private QuestionDTO dto;

    @BeforeEach
    void setUp() {
        survey = new Survey("Title");
        dto = new QuestionDTO();
    }

    @Test
    void testCreateAndSaveSimpleQuestion() {
        dto.setType(QuestionTypes.SIMPLE);
        doNothing().when(simpleQuestionFactory).createAndSaveQuestion(survey, dto);

        assertAll(() -> questionMaker.createAndSaveQuestion(survey, dto));
    }

    @Test
    void testCreateAndSaveScaleQuestion() {
        dto.setType(QuestionTypes.SCALE);
        doNothing().when(scaleQuestionFactory).createAndSaveQuestion(survey, dto);

        assertAll(() -> questionMaker.createAndSaveQuestion(survey, dto));
    }

    @Test
    void testCreateAndSaveMultipleChoiceQuestion() {
        dto.setType(QuestionTypes.MULTIPLE_CHOICES);
        doNothing().when(multipleChoiceQuestionFactory).createAndSaveQuestion(survey, dto);

        assertAll(() -> questionMaker.createAndSaveQuestion(survey, dto));
    }
}