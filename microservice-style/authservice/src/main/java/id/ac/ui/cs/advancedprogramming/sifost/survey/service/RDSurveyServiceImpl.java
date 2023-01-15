package id.ac.ui.cs.advancedprogramming.sifost.survey.service;

import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.Role;
import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.User;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.InterestRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.RoleRepository;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
public class RDSurveyServiceImpl implements RDSurveyService {

    @Autowired
    SurveyRepository surveyRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    InterestRepository interestRepository;

    @Override
    public List<Survey> getSurveysByOwner(User owner) {
        if (isAdmin(owner)) {
            return surveyRepository.findAll();
        }

        return surveyRepository.findByOwner(owner);
    }

    private boolean isAdmin(User user) {
        var adminRole = roleRepository.findByName("ADMIN").get(0);
        Set<Role> userRoles = user.getRoles();
        return userRoles.contains(adminRole);
    }

    @Transactional
    @Override
    public void deleteSurveyById(Long surveyId, User deleter) throws EntityNotFoundException, ForbiddenDeletionException {
        var survey = surveyRepository.getById(surveyId);

        var adminRole = roleRepository.findByName("ADMIN").get(0);
        Set<Role> role = deleter.getRoles();

        if (role.contains(adminRole) || survey.getOwner().equals(deleter)) {
            var interest = survey.getInterest();
            interest.notifySurveyDeletion(survey);

            surveyRepository.delete(survey);
        } else {
            throw new ForbiddenDeletionException();
        }
    }
}
