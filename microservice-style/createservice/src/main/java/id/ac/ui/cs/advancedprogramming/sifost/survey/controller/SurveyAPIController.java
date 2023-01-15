package id.ac.ui.cs.advancedprogramming.sifost.survey.controller;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.dto.SurveyDTO;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.QuestionTypes;
import id.ac.ui.cs.advancedprogramming.sifost.survey.service.ForbiddenDeletionException;
import id.ac.ui.cs.advancedprogramming.sifost.survey.service.SurveyAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SurveyAPIController {

    @Autowired
    SurveyAPIService surveyAPIService;

    @GetMapping("/interests")
    @ResponseBody
    public ResponseEntity<Iterable<String>> getAllInterests() {
        return ResponseEntity.ok(surveyAPIService.getAllInterestsName());
    }

    @GetMapping("/survey/{surveyId}")
    @ResponseBody
    public ResponseEntity<Survey> getSurveyById(@PathVariable("surveyId") long surveyId) {
        var survey = surveyAPIService.getSurveyById(surveyId);
        if (survey.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(survey.get());
    }
    @GetMapping("/survey")
    @ResponseBody
    public ResponseEntity<Iterable<Survey>> getSurveyList(@RequestParam Long ownerId) {
        return ResponseEntity.ok(surveyAPIService.getSurveysByOwner(ownerId));
    }

    @GetMapping("/question-types")
    public ResponseEntity<List<QuestionTypes>> getQuestionTypes() {
        return ResponseEntity.ok(surveyAPIService.getQuestionTypes());
    }

    @PostMapping("/submit")
    public ResponseEntity<Long> submitForm(@RequestBody SurveyDTO dto) {
        var survey = surveyAPIService.createSurvey(dto);
        return ResponseEntity.ok(survey.getSurveyId());
    }

    @GetMapping("/delete")
    @ResponseBody
    public ResponseEntity<String> deleteSurvey(@RequestParam Long surveyId, @RequestParam Long ownerId) {
        try {
            surveyAPIService.deleteSurveyById(surveyId, ownerId);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Survey not found", HttpStatus.NOT_FOUND);
        } catch (ForbiddenDeletionException e) {
            return new ResponseEntity<>("You are not authorized to delete this", HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok("deleted");
    }
}
