package id.ac.ui.cs.advancedprogramming.sifost.auth;

import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.Interest;
import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.Role;
import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.User;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.InterestRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.RoleRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class AuthInitializer {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    InterestRepository interestRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){
        roleRepository.save(new Role("USER"));
        roleRepository.save(new Role("ADMIN"));
        interestRepository.save(new Interest("pendidikan"));
        interestRepository.save(new Interest("politik"));
        interestRepository.save(new Interest("komputer"));

        createAdminAcount();
    }

    public void createAdminAcount() {
        var adminAccount = new User();
        adminAccount.setUsername("admin");
        adminAccount.setPassword(passwordEncoder.encode("sifost-admin"));
        adminAccount.setEmail("admin@sifost.com");

        var adminRole = roleRepository.findByName("ADMIN").get(0);
        Set<Role> accountRoles = adminAccount.getRoles();
        accountRoles.add(adminRole);
        adminAccount.setRoles(accountRoles);
        adminAccount.setEnabled(true);

        userRepository.save(adminAccount);
    }
}
