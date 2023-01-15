package id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Table(name = "multipleChoiceQuestion")
@Entity
@Getter
@Data
@NoArgsConstructor
public class MultipleChoiceQuestion implements Question{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long questionId;

    @ManyToOne
    @JoinColumn(name = "survey")
    private Survey survey;

    @Column(name = "prompt")
    private String prompt;

    @ElementCollection
    private List<String> choices;
    @ElementCollection
    private List<Integer> answers;

    @ElementCollection
    private Map<String, Integer> result;

    // This will be used when we generate our result
    private int answersNo;

    private QuestionTypes type = QuestionTypes.MULTIPLE_CHOICES;

    public MultipleChoiceQuestion(Survey survey, String prompt, List<String> choices) {
        this.survey = survey;
        this.prompt = prompt;
        this.choices = choices;
        this.result = genMap(choices);
        this.answersNo = 0;
    }

    public String getPrompt() {
        return this.prompt;
    }

    public QuestionTypes getType() {
        return this.type;
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
