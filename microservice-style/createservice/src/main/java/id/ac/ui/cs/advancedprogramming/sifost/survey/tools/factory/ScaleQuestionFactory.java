package id.ac.ui.cs.advancedprogramming.sifost.survey.tools.factory;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.dto.QuestionDTO;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.ScaleQuestion;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.ScaleQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScaleQuestionFactory implements QuestionFactory {

    @Autowired
    ScaleQuestionRepository scaleQuestionRepository;

    @Override
    public void createAndSaveQuestion(Survey survey, QuestionDTO dto) {
        var question = new ScaleQuestion(survey, dto.getPrompt());
        scaleQuestionRepository.save(question);
        survey.addQuestion(question);
    }
}
