package id.ac.ui.cs.advancedprogramming.sifost.survey.model.dto;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.QuestionTypes;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionDTO {

    QuestionTypes type;
    String prompt;
    List<String> answers = new ArrayList<>();
}
