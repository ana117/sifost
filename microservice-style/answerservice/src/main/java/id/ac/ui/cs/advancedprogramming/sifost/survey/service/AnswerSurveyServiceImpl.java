package id.ac.ui.cs.advancedprogramming.sifost.survey.service;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;

import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.SurveyRepository;
import id.ac.ui.cs.advancedprogramming.sifost.survey.tools.handler.MultipleChoiceQuestionHandler;
import id.ac.ui.cs.advancedprogramming.sifost.survey.tools.handler.ScaleQuestionHandler;
import id.ac.ui.cs.advancedprogramming.sifost.survey.tools.handler.SimpleQuestionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AnswerSurveyServiceImpl implements AnswerSurveyService{
    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private SimpleQuestionHandler simpleQuestionHandler;
    @Autowired
    private ScaleQuestionHandler scaleQuestionHandler;
    @Autowired
    private MultipleChoiceQuestionHandler multipleChoiceQuestionHandler;
    public Survey saveAnswer(List<String> answer, Long surveyId){
        try {
            var survey = surveyRepository.getById(surveyId);

            multipleChoiceQuestionHandler.setNextHandler(simpleQuestionHandler);
            simpleQuestionHandler.setNextHandler(scaleQuestionHandler);
            return multipleChoiceQuestionHandler.handle(answer, survey);
        }
        catch(Exception e){
                return null;
            }
    }
}
