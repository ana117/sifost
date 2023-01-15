package id.ac.ui.cs.advancedprogramming.sifost.auth.service;

import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.Interest;
import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.Role;
import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.User;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.InterestRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.RoleRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InterestRepository interestRepository;
    public User registerUser(User user,Long[] interestId){
        user.setPassword(encodePassword(user.getPassword()));
        Role temp = roleRepository.findByName("USER").get(0);

        Set<Role> temp2 = user.getRoles();
        temp2.add(temp);
        user.setRoles(temp2);
        Set<Interest> interestSet = user.getInterest();
        for(Long id : interestId){
            var interest = interestRepository.getById(id);

            interestSet.add(interest);
        }
        user.setInterest(interestSet);
        userRepository.save(user);
        return user;
    }
    public String encodePassword(String password){
        var passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
    public List<User> getAllUser(){
        return userRepository.findAll();
    }
    public boolean existDuplicate(String username){
        return userRepository.getUserByUsername(username) != null;
    }
}
