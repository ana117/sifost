package id.ac.ui.cs.advancedprogramming.sifost.profile.service;

import id.ac.ui.cs.advancedprogramming.sifost.auth.CustomUserDetails;
import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.Interest;
import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.Role;
import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.User;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.InterestRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.UserRepository;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    InterestRepository interestRepository;

    @InjectMocks
    ProfileService profileService = new ProfileServiceImpl();

    User user;
    Set<Survey> surveyList;
    Survey survey;

    @BeforeEach
    void SetUp(){
        user = new User();
        user.setUsername("Akbar");
        user.setPassword("Akbar");
        user.setEmail("akbar@gmail.com");
        user.setDeskripsi("Akun Akbar");
        user.setId(1L);
        Role role = new Role("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        user.setRoles(roleSet);
        Set<Interest> interests = user.getInterest();
        interests.add(new Interest("Komputer"));
        user.setInterest(interests);
        survey = new Survey("Survey TEST");
        survey.setSurveyId(1L);
        surveyList = new HashSet<>();
        surveyList.add(survey);
        user.setSurveyNotification(surveyList);
    }

    @Test
    void getNotificationListTest(){
        when(userRepository.getUserByUsername(user.getUsername())).thenReturn(user);

        List<Survey> resultList = profileService.getNotificationList(user.getUsername());
        assertEquals(survey, resultList.get(0));
        assertEquals("Survey TEST",resultList.get(0).getTitle());
        assertEquals(1L, resultList.get(0).getSurveyId());
    }
}
