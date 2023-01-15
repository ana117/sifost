package id.ac.ui.cs.advancedprogramming.sifost.survey.tools.handler;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.MultipleChoiceQuestion;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.MultipleChoiceQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class MultipleChoiceQuestionHandler implements Handler{
    Handler nextHandler;
    @Autowired
    private MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;
    public MultipleChoiceQuestionHandler(){}
    public MultipleChoiceQuestionHandler(Handler handler){
        this.nextHandler = handler;
    }
    public Survey handle(List<String> answers, Survey survey){
        List<MultipleChoiceQuestion> temp = survey.getMultipleChoiceQuestions();

        if(!answers.isEmpty()) {
            while (!answers.isEmpty() && !answers.get(0).equals("&&&&&")){
                var noSoal = Integer.parseInt(answers.get(0).substring(0,answers.get(0).indexOf('#')));
                var ans = Integer.parseInt(answers.get(0).substring(answers.get(0).indexOf("#")+1));
                MultipleChoiceQuestion soal = temp.get(noSoal);
                List<Integer> answer = soal.getAnswers();
                answer.add(ans);
                soal.setAnswers(answer);
                multipleChoiceQuestionRepository.save(soal);
                answers.remove(0);

            }
            if(!answers.isEmpty()){
                answers.remove(0);
            }
        }
        if(nextHandler == null){
            return survey;
        }
        return nextHandler.handle(answers,survey);
    }

    @Override
    public void setNextHandler(Handler handler) {
        this.nextHandler = handler;
    }
}
