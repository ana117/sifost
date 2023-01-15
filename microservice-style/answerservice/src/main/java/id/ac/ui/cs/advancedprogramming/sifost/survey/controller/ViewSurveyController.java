package id.ac.ui.cs.advancedprogramming.sifost.survey.controller;

import id.ac.ui.cs.advancedprogramming.sifost.auth.CustomUserDetails;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.SurveyRepository;
import id.ac.ui.cs.advancedprogramming.sifost.survey.service.RDSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Controller
@RequestMapping("/survey")
public class ViewSurveyController {

    @Autowired
    SurveyRepository surveyRepository;

    @Autowired
    RDSurveyService rdSurveyService;

    @GetMapping("/view/{surveyId}")
    public String getViewPage(@PathVariable("surveyId") int surveyId, Model model) {
        try {
            var survey = surveyRepository.getById((long) surveyId);
            model.addAttribute("survey", survey);
            return "survey/view-survey";
        } catch (EntityNotFoundException e) {
            return "survey/error";
        }
    }

    @GetMapping("/my-survey")
    public String getMySurveyPage(Model model, Authentication authentication) {
        var user = (CustomUserDetails) authentication.getPrincipal();
        List<Survey> surveys = rdSurveyService.getSurveysByOwner(user.getUser());
        model.addAttribute("surveys", surveys);
        return "survey/my-survey";
    }
}
