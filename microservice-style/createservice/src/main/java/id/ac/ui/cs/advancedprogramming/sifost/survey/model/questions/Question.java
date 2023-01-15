package id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions;

import com.fasterxml.jackson.annotation.JsonBackReference;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import lombok.Getter;

import javax.persistence.*;

@MappedSuperclass
@Getter
public abstract class Question {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long questionId;

    @ManyToOne
    @JoinColumn(name = "survey")
    @JsonBackReference
    protected Survey survey;

    @Column(name = "prompt")
    protected String prompt;

    protected QuestionTypes type;
}
