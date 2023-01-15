package id.ac.ui.cs.advancedprogramming.sifost.survey.tools.factory;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.dto.QuestionDTO;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.SimpleQuestion;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.SimpleQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimpleQuestionFactory implements QuestionFactory {

    @Autowired
    SimpleQuestionRepository simpleQuestionRepository;

    @Override
    public void createAndSaveQuestion(Survey survey, QuestionDTO dto) {
        var question = new SimpleQuestion(survey, dto.getPrompt());
        simpleQuestionRepository.save(question);
        survey.addQuestion(question);
    }
}
