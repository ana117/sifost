package id.ac.ui.cs.advancedprogramming.sifost.survey.service;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.dto.SurveyDTO;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.QuestionTypes;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.MultipleChoiceQuestionRepository;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.ScaleQuestionRepository;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.SimpleQuestionRepository;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.SurveyRepository;
import id.ac.ui.cs.advancedprogramming.sifost.survey.tools.QuestionMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CreateSurveyServiceImpl implements CreateSurveyService{

    @Autowired
    SurveyRepository surveyRepository;

    @Autowired
    SimpleQuestionRepository simpleQuestionRepository;

    @Autowired
    MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;

    @Autowired
    ScaleQuestionRepository scaleQuestionRepository;

    @Autowired
    QuestionMaker questionMaker;

    @Override
    public List<QuestionTypes> getQuestionTypes() {
        return List.of(QuestionTypes.values());
    }

    @Override
    public Survey getSurveyById(Long surveyId) throws EntityNotFoundException {
        return surveyRepository.getById(surveyId);
    }

    @Override
    public Survey createSurvey(SurveyDTO dto) {
        var survey = new Survey(dto.getTitle());
        surveyRepository.save(survey);

        dto.getQuestions().forEach(question -> questionMaker.createAndSaveQuestion(survey, question));

        return survey;
    }
}
