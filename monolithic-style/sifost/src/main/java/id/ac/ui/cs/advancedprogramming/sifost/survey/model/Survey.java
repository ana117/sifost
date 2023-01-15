package id.ac.ui.cs.advancedprogramming.sifost.survey.model;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.MultipleChoiceQuestion;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.Question;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.ScaleQuestion;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.SimpleQuestion;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "survey")
@Entity
@Data
@NoArgsConstructor
public class Survey {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long surveyId;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "survey")
    private List<SimpleQuestion> simpleQuestions = new ArrayList<>();

    @OneToMany(mappedBy = "survey")
    private List<MultipleChoiceQuestion> multipleChoiceQuestions = new ArrayList<>();

    @OneToMany(mappedBy = "survey")
    private List<ScaleQuestion> scaleQuestions = new ArrayList<>();

    public Survey(String title) {
        this.title = title;
    }

    public void addQuestion(SimpleQuestion q) {
        this.simpleQuestions.add(q);
    }

    public void addQuestion(MultipleChoiceQuestion q) {
        this.multipleChoiceQuestions.add(q);
    }

    public void addQuestion(ScaleQuestion q) {
        this.scaleQuestions.add(q);
    }

    public List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.addAll(simpleQuestions);
        questions.addAll(multipleChoiceQuestions);
        questions.addAll(scaleQuestions);
        return questions;
    }
}
