package id.ac.ui.cs.advancedprogramming.sifost.auth.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

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

    public Interest(String name){
        this.name = name;
    }
}
