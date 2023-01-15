package id.ac.ui.cs.advancedprogramming.sifost.survey.tools.factory;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.dto.QuestionDTO;

public interface QuestionFactory {
    void createAndSaveQuestion(Survey survey, QuestionDTO questionDTO);
}
