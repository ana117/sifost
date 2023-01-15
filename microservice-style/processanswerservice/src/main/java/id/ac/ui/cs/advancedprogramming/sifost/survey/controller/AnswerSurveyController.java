package id.ac.ui.cs.advancedprogramming.sifost.survey.controller;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.dto.AnswerDTO;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.SurveyRepository;
import id.ac.ui.cs.advancedprogramming.sifost.survey.service.AnswerSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/survey")
public class AnswerSurveyController {

    @Autowired
    SurveyRepository surveyRepository;
    @Autowired
    AnswerSurveyService answerSurveyService;

    static final String FAILED_SAVE = "jawaban gagal disimpan, silahkan isi ulang survey";

    @PostMapping(value = "/save",produces = {MimeTypeUtils.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> savingSurvey(@RequestParam(required = false,value = "answer") List<String> answer,@RequestParam(required = false,value = "surveyId") Long surveyId, Model model) {

        try{
            var res = answerSurveyService.saveAnswer(answer,surveyId);
            if(res  == null){
                return new ResponseEntity<>(FAILED_SAVE,HttpStatus.OK);
            }

            return new ResponseEntity<>("jawaban disimpan", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(FAILED_SAVE,HttpStatus.OK);
        }
    }

    @PostMapping("/process")
    public String saveSurvey(@RequestParam(required = false,value = "answer") List<String> answer,@RequestParam(required = false,value = "surveyId") Long surveyId,AnswerDTO answerDTO, Model model) {

        try{

            if(answerDTO.getAnswers() != null) {
                for (var i = 0; i < answerDTO.getAnswers().size(); i++) {
                    answer.add(answerDTO.getAnswers().get(i));

                }
            }
            answerDTO.setAnswers(answer);
            model.addAttribute("answerDTO",answerDTO);
            model.addAttribute("surveyId",surveyId);
            model.addAttribute("model",model);

            return "survey/answer-finish";
        } catch (Exception e) {
            return "survey/error";
        }
    }
    @GetMapping("/answer/{surveyId}")
    public String getViewPage(@PathVariable("surveyId") int surveyId, Model model) {
        try {
            var survey = surveyRepository.getById((long) surveyId);
            model.addAttribute("survey", survey);
            var answerDto = new AnswerDTO();
            List<String> temp = new ArrayList<>();
            for(var i = 0;i < survey.getScaleQuestions().size();i++){
                temp.add("");
            }
            answerDto.setAnswers(temp);
            model.addAttribute("answerDTO",answerDto);
            return "survey/answer-survey";
        } catch (EntityNotFoundException e) {
            return "survey/error";
        }
    }
}
