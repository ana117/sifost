package id.ac.ui.cs.advancedprogramming.sifost.survey.service;

import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.Role;
import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.User;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.RoleRepository;
import id.ac.ui.cs.advancedprogramming.sifost.survey.model.Survey;
import id.ac.ui.cs.advancedprogramming.sifost.survey.repository.SurveyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RDSurveyServiceImplTest {

    @Mock
    SurveyRepository surveyRepository;

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    RDSurveyService rdSurveyService = new RDSurveyServiceImpl();

    private User user1;
    private User user2;
    private User admin;

    private Survey survey1;
    private Survey survey2;

    private List<Survey> expectedSurveys;
    private List<Role> expectedGetAdminRoles;

    @BeforeEach
    void setUp() {
        Role userRole = new Role("USER");
        Role adminRole = new Role("ADMIN");

        user1 = new User();
        user1.setId(1L);
        user1.setUsername("u1");
        Set<Role> tempRole = user1.getRoles();
        tempRole.add(userRole);
        user1.setRoles(tempRole);

        user2 = new User();
        user2.setId(2L);
        user2.setUsername("u2");
        tempRole = user2.getRoles();
        tempRole.add(userRole);
        user2.setRoles(tempRole);

        admin = new User();
        admin.setId(3L);
        admin.setUsername("a");
        tempRole = admin.getRoles();
        tempRole.add(adminRole);
        admin.setRoles(tempRole);

        survey1 = new Survey("title1");
        survey1.setOwner(user1);

        survey2 = new Survey("title2");
        survey2.setOwner(user2);

        expectedSurveys = new ArrayList<>();
        expectedGetAdminRoles = new ArrayList<>();
        expectedGetAdminRoles.add(adminRole);
    }

    @Test
    void testGetSurveysWithUser() {
        expectedSurveys.add(survey1);

        when(roleRepository.findByName("ADMIN")).thenReturn(expectedGetAdminRoles);
        when(surveyRepository.findByOwner(user1)).thenReturn(expectedSurveys);

        List<Survey> result = rdSurveyService.getSurveysByOwner(user1);
        assertEquals(expectedSurveys.size(), result.size());
        assertEquals(user1.getId(), result.get(0).getOwner().getId());
    }

    @Test
    void testGetSurveysWithAdmin() {
        expectedSurveys.add(survey1);
        expectedSurveys.add(survey2);

        when(roleRepository.findByName("ADMIN")).thenReturn(expectedGetAdminRoles);
        when(surveyRepository.findAll()).thenReturn(expectedSurveys);

        List<Survey> result = rdSurveyService.getSurveysByOwner(admin);
        assertEquals(expectedSurveys.size(), result.size());
    }

    @Test
    void deleteSurveyWithCorrectOwner() {
        when(roleRepository.findByName("ADMIN")).thenReturn(expectedGetAdminRoles);
        when(surveyRepository.getById(1L)).thenReturn(survey1);

        rdSurveyService.deleteSurveyById(1L, user1);
        verify(surveyRepository, times(1)).delete(survey1);
    }

    @Test
    void deleteSurveyWithIncorrectOwner() {
        when(roleRepository.findByName("ADMIN")).thenReturn(expectedGetAdminRoles);
        when(surveyRepository.getById(1L)).thenReturn(survey1);

        assertThrows(ForbiddenDeletionException.class,
                () -> rdSurveyService.deleteSurveyById(1L, user2));
    }

    @Test
    void deleteSurveyWithAdmin() {
        when(roleRepository.findByName("ADMIN")).thenReturn(expectedGetAdminRoles);
        when(surveyRepository.getById(1L)).thenReturn(survey1);

        rdSurveyService.deleteSurveyById(1L, admin);
        verify(surveyRepository, times(1)).delete(survey1);
    }
}