package id.ac.ui.cs.advancedprogramming.sifost.profile.service;

import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;

import java.util.List;

public interface ProfileService {
    List<Survey> getNotificationList(String username);
}
