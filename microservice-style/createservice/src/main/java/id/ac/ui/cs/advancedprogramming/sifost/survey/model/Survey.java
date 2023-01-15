package id.ac.ui.cs.advancedprogramming.sifost.survey.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.Interest;
import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.User;
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

    @ManyToOne
    private User owner;

    @Column(name = "status")
    private boolean isActive = true;

    @Column(name = "publish")
    private boolean isPublish = false;

    @OneToMany(mappedBy = "survey")
    @JsonManagedReference
    private List<SimpleQuestion> simpleQuestions = new ArrayList<>();

    @OneToMany(mappedBy = "survey")
    @JsonManagedReference
    private List<MultipleChoiceQuestion> multipleChoiceQuestions = new ArrayList<>();

    @OneToMany(mappedBy = "survey")
    @JsonManagedReference
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

    @ManyToOne
    @JsonIgnore
    private Interest interest = new Interest();

    public void addInterest(Interest interest){
        this.interest = interest;
    }

    @JsonIgnore
    public List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.addAll(simpleQuestions);
        questions.addAll(multipleChoiceQuestions);
        questions.addAll(scaleQuestions);
        return questions;
    }

    public String getLink(){
        return "/survey/answer/"+this.getSurveyId();
    }

    public boolean getIsActive() {return this.isActive;}

    public void setIsActive(boolean status) {this.isActive = status;}

    public boolean getIsPublish(){return this.isPublish;}

    public void setIsPublish(boolean status) {this.isPublish = status;}
}
