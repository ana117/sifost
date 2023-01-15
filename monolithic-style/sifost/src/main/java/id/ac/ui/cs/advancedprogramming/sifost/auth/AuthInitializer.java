package id.ac.ui.cs.advancedprogramming.sifost.auth;

import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.Interest;
import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.Role;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.InterestRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.RoleRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AuthInitializer {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    InterestRepository interestRepository;
    @PostConstruct
    public void init(){
        roleRepository.save(new Role("USER"));
        interestRepository.save(new Interest("pendidikan"));
        interestRepository.save(new Interest("politik"));
        interestRepository.save(new Interest("komputer"));
    }
}
