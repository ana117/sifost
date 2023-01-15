package id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    private QuestionTypes type = QuestionTypes.MULTIPLE_CHOICES;

    public MultipleChoiceQuestion(Survey survey, String prompt, List<String> choices) {
        this.survey = survey;
        this.prompt = prompt;
        this.choices = choices;
    }

    public String getPrompt() {
        return this.prompt;
    }

    public QuestionTypes getType() {
        return this.type;
    }
}
