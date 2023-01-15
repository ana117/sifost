package id.ac.ui.cs.advancedprogramming.sifost.auth.service;

import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.Interest;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.InterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterestServiceImpl implements InterestService {
    @Autowired
    InterestRepository interestRepository;
    public List<Interest> getAllInterest(){
        return interestRepository.findAll();
    }
}
