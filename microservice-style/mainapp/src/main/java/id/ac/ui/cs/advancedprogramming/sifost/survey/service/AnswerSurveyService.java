package id.ac.ui.cs.advancedprogramming.sifost.survey.service;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import java.util.List;

public interface AnswerSurveyService {
    Survey saveAnswer(List<String> answer, Long idSurvey);
}
