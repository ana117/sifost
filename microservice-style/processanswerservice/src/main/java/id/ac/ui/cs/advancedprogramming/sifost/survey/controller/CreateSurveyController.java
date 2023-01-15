package id.ac.ui.cs.advancedprogramming.sifost.survey.controller;

import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.Interest;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.InterestRepository;
import id.ac.ui.cs.advancedprogramming.sifost.survey.service.CreateSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/survey")
public class CreateSurveyController {

    @Autowired
    CreateSurveyService createSurveyService;

    @Autowired
    InterestRepository interestRepository;

    @GetMapping("/create")
    public String getCreateSurveyPage(Model model) {
        List<Interest> interestList = interestRepository.findAll();
        model.addAttribute("listInterest", interestList);
        return "survey/create-survey";
    }

    @GetMapping("/success/{surveyId}")
    public String getSuccessPage(HttpServletRequest request, @PathVariable("surveyId") int surveyId, Model model) {
        String path = request.getRequestURL().toString().replace("success","view");
        model.addAttribute("link", path);
        return "survey/create-success";
    }
}




