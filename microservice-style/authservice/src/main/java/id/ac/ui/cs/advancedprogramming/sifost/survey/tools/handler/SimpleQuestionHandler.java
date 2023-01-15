package id.ac.ui.cs.advancedprogramming.sifost.survey.tools.handler;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.SimpleQuestion;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.SimpleQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class SimpleQuestionHandler implements Handler{
    Handler nextHandler;
    @Autowired
    private SimpleQuestionRepository simpleQuestionRepository;

    public SimpleQuestionHandler(){}
    public SimpleQuestionHandler(Handler handler){
        this.nextHandler = handler;
    }
    public Survey handle(List<String> answers, Survey survey){
        List<SimpleQuestion> temp = survey.getSimpleQuestions();
        if(!answers.isEmpty()) {
            for(var i = 0;i < temp.size();i++){
                SimpleQuestion curr = temp.get(i);
                List<String> answer = curr.getAnswers();
                answer.add(answers.get(0));
                curr.setAnswers(answer);
                simpleQuestionRepository.save(curr);
                answers.remove(0);
            }
        }
        if(!answers.isEmpty()){
            answers.remove(0);
        }
        return nextHandler.handle(answers,survey);
    }
    @Override
    public void setNextHandler(Handler handler) {
        this.nextHandler = handler;
    }
}
