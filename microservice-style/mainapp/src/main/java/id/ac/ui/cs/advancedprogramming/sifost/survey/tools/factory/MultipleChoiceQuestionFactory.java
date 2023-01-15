package id.ac.ui.cs.advancedprogramming.sifost.survey.tools.factory;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.dto.QuestionDTO;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.MultipleChoiceQuestion;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.MultipleChoiceQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MultipleChoiceQuestionFactory implements QuestionFactory {

    @Autowired
    MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;

    @Override
    public void createAndSaveQuestion(Survey survey, QuestionDTO dto) {
        var question = new MultipleChoiceQuestion(survey, dto.getPrompt(), dto.getAnswers());
        multipleChoiceQuestionRepository.save(question);
        survey.addQuestion(question);
    }
}
