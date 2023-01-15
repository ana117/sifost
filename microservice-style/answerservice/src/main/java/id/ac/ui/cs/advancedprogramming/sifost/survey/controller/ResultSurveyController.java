package id.ac.ui.cs.advancedprogramming.sifost.survey.controller;

import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.SurveyRepository;
import id.ac.ui.cs.advancedprogramming.sifost.survey.service.ResultSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;

//  This is mostly a copy of ViewSurvey but with extra stuff
@Controller
@RequestMapping("/survey")
public class ResultSurveyController {
    @Autowired
    SurveyRepository surveyRepository;

    @Autowired
    ResultSurveyService resultSurveyService;

    @GetMapping("/result/{surveyId}")
    public String getViewPage(@PathVariable("surveyId") int surveyId, Model model) {
        try {
            var survey = surveyRepository.getById((long) surveyId);
            var surveyResult = resultSurveyService.getResult(survey);
            model.addAttribute("survey", surveyResult);
            return "survey/result-survey";
        } catch (EntityNotFoundException e) {
            return "survey/error";
        }
    }
}
