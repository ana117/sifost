package id.ac.ui.cs.advancedprogramming.sifost.profile.service;

import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.InterestRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.UserRepository;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    InterestRepository interestRepository;

    @Override
    public List<Survey> getNotificationList(String username) {
        var user = userRepository.getUserByUsername(username);
        List<Survey> surveyList = new ArrayList<>(user.getSurveyNotification());
        surveyList.sort(Comparator.comparing(Survey::getSurveyId));
        return surveyList;
    }

}
