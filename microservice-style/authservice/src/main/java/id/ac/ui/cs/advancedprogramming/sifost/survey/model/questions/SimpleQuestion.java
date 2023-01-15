package id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Table(name = "simpleQuestion")
@Entity
@Getter
@Data
@NoArgsConstructor
public class SimpleQuestion implements Question{

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
    private List<String> answers;

    private QuestionTypes type = QuestionTypes.SIMPLE;

    public SimpleQuestion(Survey survey, String prompt) {
        this.survey = survey;
        this.prompt = prompt;
    }

    public String getPrompt() {
        return this.prompt;
    }

    public QuestionTypes getType() {
        return this.type;
    }

    public List<String> getAnswers(){return this.answers;}
}
