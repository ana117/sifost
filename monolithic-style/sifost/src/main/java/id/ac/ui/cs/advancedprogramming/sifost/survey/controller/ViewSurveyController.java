package id.ac.ui.cs.advancedprogramming.sifost.survey.controller;

import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;


@Controller
@RequestMapping("/survey")
public class ViewSurveyController {

    @Autowired
    SurveyRepository surveyRepository;

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
}
