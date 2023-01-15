package id.ac.ui.cs.advancedprogramming.sifost.Auth.Service;

import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.Interest;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.InterestRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.service.InterestService;
import id.ac.ui.cs.advancedprogramming.sifost.auth.service.InterestServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InterestServiceImplTest {
    @Mock
    private InterestRepository interestRepository;
    @InjectMocks
    private InterestService interestService = new InterestServiceImpl();
    @Test
    void getAllInterestTest(){
        Interest interest = new Interest("komputer");
        List<Interest> interests = new ArrayList();
        interests.add(interest);
        when(interestRepository.findAll()).thenReturn(interests);
        List<Interest> result = interestService.getAllInterest();
        assertEquals("komputer",result.get(0).getName());

    }
}
