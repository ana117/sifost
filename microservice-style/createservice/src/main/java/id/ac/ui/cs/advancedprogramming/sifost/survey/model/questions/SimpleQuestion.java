package id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Table(name = "simpleQuestion")
@Entity
@Getter
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SimpleQuestion extends Question{
    @ElementCollection
    private List<String> answers;

    public SimpleQuestion(Survey survey, String prompt) {
        this.survey = survey;
        this.prompt = prompt;
        this.type = QuestionTypes.SIMPLE;
    }

    public List<String> getAnswers(){return this.answers;}
}
