package id.ac.ui.cs.advancedprogramming.sifost.survey.tools.handler;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.ScaleQuestion;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.ScaleQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ScaleQuestionHandler implements Handler{
    Handler nextHandler;
    @Autowired
    private ScaleQuestionRepository scaleQuestionRepository;

    public ScaleQuestionHandler(){}
    public ScaleQuestionHandler(Handler handler){
        this.nextHandler = handler;
    }
    public Survey handle(List<String> answers, Survey survey){
        List<ScaleQuestion> temp = survey.getScaleQuestions();
        if(!answers.isEmpty()) {
            for(var i = 0;i < temp.size();i++){
                ScaleQuestion curr = temp.get(i);
                List<Integer> answer = curr.getAnswers();
                answer.add(Integer.parseInt(answers.get(0)));
                curr.setAnswers(answer);
                scaleQuestionRepository.save(curr);
                answers.remove(0);
            }
        }
        if(!answers.isEmpty()){
            answers.remove(0);
        }
        if(nextHandler != null) {
            return nextHandler.handle(answers, survey);
        }
        else {
            return survey;
        }
    }
    @Override
    public void setNextHandler(Handler handler) {
        this.nextHandler = handler;
    }
}
