package id.ac.ui.cs.advancedprogramming.sifost.auth.entity;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "interests")
@Data
@NoArgsConstructor
public class Interest implements Serializable {
    @Id
    @Column(name = "interest_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "interest")
    private Set<User> usersSet = new HashSet<>();

    public Interest(String name){
        this.name = name;
    }

    public void notifySubscriberHelper(Survey survey){
        for (User user : usersSet){
            user.updateSurveyNotification(survey);
        }
    }

    public void notifySurveyDeletion(Survey survey) {
        for (User user : usersSet) {
            user.deleteSurveyNotification(survey);
        }
    }

    @Override
    public String toString(){
        return this.getName();
    }
}
