package id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Table(name = "scaleQuestion")
@Entity
@Getter
@Data
@NoArgsConstructor
public class ScaleQuestion implements Question{

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
    private List<Integer> answers;

    // I think Thymeleaf can only use String as key.
    @ElementCollection
    private Map<String, Integer> result;

    // This will be used when we generate our result
    private int answersNo;

    private QuestionTypes type = QuestionTypes.SCALE;



    public ScaleQuestion(Survey survey, String prompt) {
        this.survey = survey;
        this.prompt = prompt;
        this.result = genMap();
        this.answersNo = 0;
    }

    public String getPrompt() {
        return this.prompt;
    }

    public QuestionTypes getType() {
        return this.type;
    }

    public List<Integer> getAnswers(){return this.answers;}

    private Map<String, Integer> genMap() {
        Map<String, Integer> outMap = new HashMap<>();
        for (var s = 1; s <= 5; s++) {
            outMap.put(Integer.toString(s), 0);
        }
        return outMap;
    }
}
