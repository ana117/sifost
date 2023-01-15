package id.ac.ui.cs.advancedprogramming.sifost.survey.service;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.dto.SurveyDTO;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.QuestionTypes;

import java.util.List;
import java.util.Optional;

public interface SurveyAPIService {

    List<String> getAllInterestsName();

    Optional<Survey> getSurveyById(long surveyId);

    List<Survey> getSurveysByOwner(Long ownerId);

    List<QuestionTypes> getQuestionTypes();

    Survey createSurvey(SurveyDTO dto);

    void triggerNotification(Survey survey);

    void deleteSurveyById(Long surveyId, Long ownerId);
}
