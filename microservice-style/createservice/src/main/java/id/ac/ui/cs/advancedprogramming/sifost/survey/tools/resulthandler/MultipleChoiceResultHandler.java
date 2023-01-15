package id.ac.ui.cs.advancedprogramming.sifost.survey.tools.resulthandler;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.MultipleChoiceQuestion;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.MultipleChoiceQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class MultipleChoiceResultHandler implements ResultHandler {
    ResultHandler nextHandler;
    @Autowired
    private MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;

    public MultipleChoiceResultHandler(){}

    public MultipleChoiceResultHandler(ResultHandler handler){this.nextHandler = handler;}

    @Override
    public Survey handle(Survey survey) {
        List<MultipleChoiceQuestion> temp = survey.getMultipleChoiceQuestions();
        var tempIndex = 0;

        while(tempIndex < temp.size()) {
            MultipleChoiceQuestion tempQuestion = temp.get(tempIndex);
            List<String> tempChoice = tempQuestion.getChoices();
            List<Integer> tempAnswer = tempQuestion.getAnswers();
            Map<String, Integer> tempMap = tempQuestion.getResult();
            int tempNo = tempQuestion.getAnswersNo();
            for (int x = tempNo; x < tempAnswer.size(); x++) {
                int ans = tempAnswer.get(x);
                String cho = tempChoice.get(ans);
                tempMap.put(cho, tempMap.get(cho)+1);
            }
            tempQuestion.setResult(tempMap);
            tempQuestion.setAnswersNo(tempAnswer.size());
            multipleChoiceQuestionRepository.save(tempQuestion);
            tempIndex++;
        }

        return nextHandler.handle(survey);
    }

    @Override
    public void setNextHandler(ResultHandler handler) {this.nextHandler = handler;}
}
