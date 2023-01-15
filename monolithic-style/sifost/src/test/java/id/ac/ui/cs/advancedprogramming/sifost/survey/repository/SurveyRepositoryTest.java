package id.ac.ui.cs.advancedprogramming.sifost.survey.repository;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.MultipleChoiceQuestion;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.ScaleQuestion;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.SimpleQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SurveyRepositoryTest {

    @Autowired
    SurveyRepository surveyRepository;



    private Survey survey;

    @BeforeEach
    void setUp() {
        survey = new Survey("Title");
    }

    @Test
    void testSaveSurveyWithScaleQuestion() {
        ScaleQuestion question = new ScaleQuestion(survey, "scale");
        survey.addQuestion(question);

        Survey saved = surveyRepository.save(survey);

        assertEquals(survey.getTitle(), saved.getTitle());
        assertIterableEquals(survey.getScaleQuestions(), saved.getScaleQuestions());
        assertIterableEquals(survey.getQuestions(), saved.getQuestions());
    }

    @Test
    void testSaveSurveyWithSimpleQuestion() {
        SimpleQuestion question = new SimpleQuestion(survey, "simple");
        survey.addQuestion(question);

        Survey saved = surveyRepository.save(survey);

        assertEquals(survey.getTitle(), saved.getTitle());
        assertIterableEquals(survey.getSimpleQuestions(), saved.getSimpleQuestions());
        assertIterableEquals(survey.getQuestions(), saved.getQuestions());
    }

    @Test
    void testSaveSurveyWithMultipleChoiceQuestion() {
        MultipleChoiceQuestion question = new MultipleChoiceQuestion(survey, "simple", null);
        survey.addQuestion(question);

        Survey saved = surveyRepository.save(survey);

        assertEquals(survey.getTitle(), saved.getTitle());
        assertIterableEquals(survey.getMultipleChoiceQuestions(), saved.getMultipleChoiceQuestions());
        assertIterableEquals(survey.getQuestions(), saved.getQuestions());
    }

    @Test
    void testSaveSurveyWithMultipleQuestions() {
        SimpleQuestion question1 = new SimpleQuestion(survey, "simple");
        survey.addQuestion(question1);
        MultipleChoiceQuestion question2 = new MultipleChoiceQuestion(survey, "simple", null);
        survey.addQuestion(question2);
        ScaleQuestion question3 = new ScaleQuestion(survey, "scale");
        survey.addQuestion(question3);

        Survey saved = surveyRepository.save(survey);

        assertEquals(survey.getTitle(), saved.getTitle());
        assertIterableEquals(survey.getSimpleQuestions(), saved.getSimpleQuestions());
        assertIterableEquals(survey.getMultipleChoiceQuestions(), saved.getMultipleChoiceQuestions());
        assertIterableEquals(survey.getScaleQuestions(), saved.getScaleQuestions());
        assertIterableEquals(survey.getQuestions(), saved.getQuestions());
    }
}