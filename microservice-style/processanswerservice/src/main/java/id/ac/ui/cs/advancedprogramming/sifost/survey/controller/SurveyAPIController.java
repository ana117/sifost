package id.ac.ui.cs.advancedprogramming.sifost.survey.controller;

import id.ac.ui.cs.advancedprogramming.sifost.auth.CustomUserDetails;
import id.ac.ui.cs.advancedprogramming.sifost.survey.service.ForbiddenDeletionException;
import id.ac.ui.cs.advancedprogramming.sifost.survey.service.RDSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/survey/api")
public class SurveyAPIController {

    @Autowired
    RDSurveyService rdSurveyService;

    @GetMapping("/delete/{surveyId}")
    @ResponseBody
    public ResponseEntity<String> deleteSurvey(@PathVariable("surveyId") long surveyId,
                                               @AuthenticationPrincipal CustomUserDetails customUser) {
        try {
            rdSurveyService.deleteSurveyById(surveyId, customUser.getUser());
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Survey not found", HttpStatus.NOT_FOUND);
        } catch (ForbiddenDeletionException e) {
            return new ResponseEntity<>("You are not authorized to delete this", HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok("deleted");
    }
}
