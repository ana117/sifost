package id.ac.ui.cs.advancedprogramming.sifost.survey.service;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.dto.SurveyDTO;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.QuestionTypes;

import java.util.List;

public interface CreateSurveyService {
    List<QuestionTypes> getQuestionTypes();

    Survey getSurveyById(Long surveyId);

    Survey createSurvey(SurveyDTO survey);
}
