package id.ac.ui.cs.advancedprogramming.sifost.survey.controller;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.dto.SurveyDTO;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.QuestionTypes;
import id.ac.ui.cs.advancedprogramming.sifost.survey.service.CreateSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/survey/api")
public class CreateSurveyAPIController {

    @Autowired
    CreateSurveyService createSurveyService;

    @GetMapping("/get-question-types")
    public ResponseEntity<List<QuestionTypes>> getQuestionTypes() {
        return ResponseEntity.ok(createSurveyService.getQuestionTypes());
    }

    @PostMapping("/submit")
    public ResponseEntity<Long> submitForm(@RequestBody SurveyDTO dto) {
        var survey = createSurveyService.createSurvey(dto);
        return ResponseEntity.ok(survey.getSurveyId());
    }
}
