package id.ac.ui.cs.advancedprogramming.sifost.survey.service;

import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.User;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;

import java.util.List;

public interface RDSurveyService {
    List<Survey> getSurveysByOwner(User owner);

    void deleteSurveyById(Long surveyId, User deleter);
}
