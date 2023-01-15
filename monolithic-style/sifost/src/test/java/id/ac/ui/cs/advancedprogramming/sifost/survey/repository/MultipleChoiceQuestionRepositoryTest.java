package id.ac.ui.cs.advancedprogramming.sifost.survey.repository;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.MultipleChoiceQuestion;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.QuestionTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MultipleChoiceQuestionRepositoryTest {

    @Autowired
    private MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;

    private MultipleChoiceQuestion multipleChoiceQuestion;

    @BeforeEach
    void setUp() {
        Survey survey = new Survey("Some Title");
        List<String> choices = new ArrayList<>();
        choices.add("Choice 1");
        choices.add("Choice 2");
        multipleChoiceQuestion = new MultipleChoiceQuestion(survey, "A prompt", choices);
    }

    @Test
    void testSaveQuestion() {
        MultipleChoiceQuestion saved = multipleChoiceQuestionRepository.save(multipleChoiceQuestion);
        assertEquals(multipleChoiceQuestion.getQuestionId(), saved.getQuestionId());
        assertEquals(multipleChoiceQuestion.getPrompt(), saved.getPrompt());
        assertEquals(QuestionTypes.MULTIPLE_CHOICES, saved.getType());
    }

    @Test
    void testGetById() {
        multipleChoiceQuestionRepository.save(multipleChoiceQuestion);
        MultipleChoiceQuestion question = multipleChoiceQuestionRepository.getById(1L);

        assertEquals(1, question.getQuestionId());
        assertEquals(multipleChoiceQuestion.getPrompt(), question.getPrompt());
        assertIterableEquals(multipleChoiceQuestion.getChoices(), question.getChoices());
        assertEquals(QuestionTypes.MULTIPLE_CHOICES, question.getType());
    }
}