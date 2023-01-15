package id.ac.ui.cs.advancedprogramming.sifost.survey.tools;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.dto.QuestionDTO;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.QuestionTypes;
import id.ac.ui.cs.advancedprogramming.sifost.survey.tools.factory.MultipleChoiceQuestionFactory;
import id.ac.ui.cs.advancedprogramming.sifost.survey.tools.factory.ScaleQuestionFactory;
import id.ac.ui.cs.advancedprogramming.sifost.survey.tools.factory.SimpleQuestionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionMaker {

    @Autowired
    private SimpleQuestionFactory simpleQuestionFactory;

    @Autowired
    private MultipleChoiceQuestionFactory multipleChoiceQuestionFactory;

    @Autowired
    private ScaleQuestionFactory scaleQuestionFactory;

    private QuestionMaker() {}

    public void createAndSaveQuestion(Survey survey, QuestionDTO dto) {
        QuestionTypes type = dto.getType();

        if (type == QuestionTypes.SIMPLE) {
            simpleQuestionFactory.createAndSaveQuestion(survey, dto);
        } else if (type == QuestionTypes.MULTIPLE_CHOICES) {
            multipleChoiceQuestionFactory.createAndSaveQuestion(survey, dto);
        } else {
            scaleQuestionFactory.createAndSaveQuestion(survey, dto);
        }
    }
}
