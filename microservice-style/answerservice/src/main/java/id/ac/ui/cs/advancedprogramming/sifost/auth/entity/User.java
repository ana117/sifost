package id.ac.ui.cs.advancedprogramming.sifost.auth.entity;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User implements Serializable {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String deskripsi;
    private String email;
    private boolean enabled = true;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_interests",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id")
    )
    private Set<Interest> interest = new HashSet<>();

    @ManyToMany
    private Set<Survey> surveyNotification = new HashSet<>();

    // remaining getters and setters are not shown for brevity
    @Override
    public boolean equals(Object o){
        if(o == null){
            return false;
        }
        if(this.getClass() != o.getClass())
        {
            return false;
        }
        var user = (User)o;
        return(this.getUsername().equals(user.getUsername()));

    }
    @Override
    public int hashCode(){
        return (id.intValue());
    }

    public void updateSurveyNotification(Survey survey){
        this.surveyNotification.add(survey);
    }

    public void addInterestBidirectional(Interest interest1, User user){
        interest.add(interest1);
        interest1.getUsersSet().add(user);
    }

    public void deleteSurveyNotification(Survey survey) {
        this.surveyNotification.remove(survey);
    }

}
