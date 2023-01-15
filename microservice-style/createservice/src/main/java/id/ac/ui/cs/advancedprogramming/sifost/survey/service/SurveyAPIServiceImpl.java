package id.ac.ui.cs.advancedprogramming.sifost.survey.service;

import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.Interest;
import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.Role;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.InterestRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.RoleRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.UserRepository;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.dto.SurveyDTO;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.questions.QuestionTypes;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.MultipleChoiceQuestionRepository;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.ScaleQuestionRepository;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.SimpleQuestionRepository;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.SurveyRepository;
import id.ac.ui.cs.advancedprogramming.sifost.survey.tools.QuestionMaker;
import id.ac.ui.cs.advancedprogramming.sifost.survey.tools.RoleChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SurveyAPIServiceImpl implements SurveyAPIService {

    @Autowired
    InterestRepository interestRepository;
    @Autowired
    SurveyRepository surveyRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    QuestionMaker questionMaker;
    @Autowired
    RoleChecker roleChecker;
    @Autowired
    MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;
    @Autowired
    ScaleQuestionRepository scaleQuestionRepository;
    @Autowired
    SimpleQuestionRepository simpleQuestionRepository;

    @Override
    public List<String> getAllInterestsName() {
        List<Interest> interests = interestRepository.findAll();
        List<String> interestNames = new ArrayList<>();
        interests.forEach(interest -> interestNames.add(interest.getName()));
        return interestNames;
    }

    @Override
    public Optional<Survey> getSurveyById(long surveyId) {
        return surveyRepository.findById(surveyId);
    }

    @Override
    public List<Survey> getSurveysByOwner(Long ownerId) {
        var owner = userRepository.findById(ownerId);
        if (owner.isEmpty()) {
            return new ArrayList<>();
        }

        if (roleChecker.isAdmin(owner.get())) {
            return surveyRepository.findAll();
        }
        return surveyRepository.findByOwner(owner.get());
    }

    @Override
    public List<QuestionTypes> getQuestionTypes() {
        return List.of(QuestionTypes.values());
    }

    @Override
    public Survey createSurvey(SurveyDTO dto) {
        var survey = new Survey(dto.getTitle());
        var interest = interestRepository.getInterestByName(dto.getInterest());
        survey.addInterest(interest);
        triggerNotification(survey);

        survey.setOwner(userRepository.getById(dto.getOwnerId()));
        surveyRepository.save(survey);

        dto.getQuestions().forEach(question -> questionMaker.createAndSaveQuestion(survey, question));

        return survey;
    }

    @Override
    public void triggerNotification(Survey survey) {
        var interest = survey.getInterest();
        interest.notifySubscriberHelper(survey);
    }

    @Override
    @Transactional
    public void deleteSurveyById(Long surveyId, Long ownerId) {
        var deleter = userRepository.getById(ownerId);
        var survey = surveyRepository.getById(surveyId);
        var surveyOwner = survey.getOwner();

        var adminRole = roleRepository.findByName("ADMIN").get(0);
        Set<Role> role = deleter.getRoles();

        if (role.contains(adminRole) || surveyOwner.getId().equals(deleter.getId())) {
            var interest = survey.getInterest();
            interest.notifySurveyDeletion(survey);

            multipleChoiceQuestionRepository.deleteAllBySurvey(survey);
            simpleQuestionRepository.deleteAllBySurvey(survey);
            scaleQuestionRepository.deleteAllBySurvey(survey);

            surveyRepository.delete(survey);
        } else {
            throw new ForbiddenDeletionException();
        }
    }
}
