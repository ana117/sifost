package id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    private QuestionTypes type = QuestionTypes.SCALE;

    public ScaleQuestion(Survey survey, String prompt) {
        this.survey = survey;
        this.prompt = prompt;
    }

    public String getPrompt() {
        return this.prompt;
    }

    public QuestionTypes getType() {
        return this.type;
    }
}
