package id.ac.ui.cs.advancedprogramming.sifost.survey.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class SurveyDTO {
    String title;
    List<QuestionDTO> questions;
}
