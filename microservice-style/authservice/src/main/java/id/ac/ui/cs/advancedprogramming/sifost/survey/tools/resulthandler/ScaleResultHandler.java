package id.ac.ui.cs.advancedprogramming.sifost.survey.tools.resulthandler;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.ScaleQuestion;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.ScaleQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ScaleResultHandler implements ResultHandler {
    ResultHandler nextHandler;

    @Autowired
    private ScaleQuestionRepository scaleQuestionRepository;

    public ScaleResultHandler(){}

    public ScaleResultHandler(ResultHandler handler){this.nextHandler = handler;}

    @Override
    public Survey handle(Survey survey) {
        List<ScaleQuestion> temp = survey.getScaleQuestions();
        var tempIndex = 0;

        while(tempIndex < temp.size()) {
            ScaleQuestion tempQuestion = temp.get(tempIndex);
            List<Integer> tempAnswer = tempQuestion.getAnswers();
            Map<String, Integer> tempMap = tempQuestion.getResult();
            int tempNo = tempQuestion.getAnswersNo();
            for (int x = tempNo; x < tempAnswer.size(); x++) {
                var ans = Integer.toString(tempAnswer.get(x));
                tempMap.put(ans, tempMap.get(ans)+1);
            }
            tempQuestion.setResult(tempMap);
            tempQuestion.setAnswersNo(tempAnswer.size());
            scaleQuestionRepository.save(tempQuestion);
            tempIndex++;
        }

        if(nextHandler != null) {
            return nextHandler.handle(survey);
        } else {
            return survey;
        }
    }

    @Override
    public void setNextHandler(ResultHandler handler) {this.nextHandler = handler;}
}
