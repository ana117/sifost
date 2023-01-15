package id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Table(name = "multipleChoiceQuestion")
@Entity
@Getter
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MultipleChoiceQuestion extends Question{

    @ElementCollection
    private List<String> choices;
    @ElementCollection
    private List<Integer> answers;

    @ElementCollection
    private Map<String, Integer> result;

    // This will be used when we generate our result
    private int answersNo;

    public MultipleChoiceQuestion(Survey survey, String prompt, List<String> choices) {
        this.survey = survey;
        this.prompt = prompt;
        this.choices = choices;
        this.result = genMap(choices);
        this.answersNo = 0;
        this.type = QuestionTypes.MULTIPLE_CHOICES;
    }

    public List<Integer> getAnswer(){return this.answers;}

    public Map<String, Integer> getResult(){return this.result;}

    // I hope this is allowed and not contribute to code smell :/
    private Map<String, Integer> genMap(List<String> choices) {
        Map<String, Integer> outMap = new HashMap<>();
        for (String c: choices) {
            outMap.put(c, 0);
        }
        return outMap;
    }
}
