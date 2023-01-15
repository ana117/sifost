package id.ac.ui.cs.advancedprogramming.sifost.survey.repository;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.SimpleQuestion;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.QuestionTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SimpleQuestionRepositoryTest {

    @Autowired
    private SimpleQuestionRepository simpleQuestionRepository;

    private SimpleQuestion simpleQuestion;

    @BeforeEach
    void setUp() {
        Survey survey = new Survey("Some Title");
        simpleQuestion = new SimpleQuestion(survey, "A prompt");
    }

    @Test
    void testSaveQuestion() {
        SimpleQuestion saved = simpleQuestionRepository.save(simpleQuestion);
        assertEquals(simpleQuestion.getQuestionId(), saved.getQuestionId());
        assertEquals(simpleQuestion.getPrompt(), saved.getPrompt());
        assertEquals(QuestionTypes.SIMPLE, saved.getType());
    }

    @Test
    void testGetById() {
        simpleQuestionRepository.save(simpleQuestion);
        SimpleQuestion question = simpleQuestionRepository.getById(1L);

        assertEquals(1, question.getQuestionId());
        assertEquals(simpleQuestion.getPrompt(), question.getPrompt());
        assertEquals(QuestionTypes.SIMPLE, question.getType());
    }
}