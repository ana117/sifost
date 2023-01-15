package id.ac.ui.cs.advancedprogramming.sifost.survey.repository;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.ScaleQuestion;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.QuestionTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ScaleQuestionRepositoryTest {

    @Autowired
    private ScaleQuestionRepository scaleQuestionRepository;

    private ScaleQuestion scaleQuestion;

    @BeforeEach
    void setUp() {
        Survey survey = new Survey("Some Title");
        scaleQuestion = new ScaleQuestion(survey, "A prompt");
    }

    @Test
    void testSaveQuestion() {
        ScaleQuestion saved = scaleQuestionRepository.save(scaleQuestion);
        assertEquals(scaleQuestion.getQuestionId(), saved.getQuestionId());
        assertEquals(scaleQuestion.getPrompt(), saved.getPrompt());
        assertEquals(QuestionTypes.SCALE, saved.getType());
    }

    @Test
    void testGetById() {
        scaleQuestionRepository.save(scaleQuestion);
        ScaleQuestion question = scaleQuestionRepository.getById(1L);

        assertEquals(1, question.getQuestionId());
        assertEquals(scaleQuestion.getPrompt(), question.getPrompt());
        assertEquals(QuestionTypes.SCALE, question.getType());
    }
}