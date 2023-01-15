package id.ac.ui.cs.advancedprogramming.sifost.survey.service;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.tools.resulthandler.MultipleChoiceResultHandler;
import id.ac.ui.cs.advancedprogramming.sifost.survey.tools.resulthandler.ScaleResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultSurveyServiceImpl implements ResultSurveyService{

    @Autowired
    private MultipleChoiceResultHandler multipleChoiceResultHandler;

    @Autowired
    private ScaleResultHandler scaleResultHandler;

    public Survey getResult(Survey survey) {
        // Since we don't need to calculate anything for SimpleQuestion (we just loop all the answers),
        // There is no ResultHandler for SimpleQuestion.
        multipleChoiceResultHandler.setNextHandler(scaleResultHandler);
        return multipleChoiceResultHandler.handle(survey);
    }
}
