package id.ac.ui.cs.advancedprogramming.sifost.survey.tools;

import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.Role;
import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.User;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RoleChecker {
    @Autowired
    private RoleRepository roleRepository;

    public boolean isAdmin(User user) {
        var adminRole = roleRepository.findByName("ADMIN").get(0);
        Set<Role> userRoles = user.getRoles();
        return userRoles.contains(adminRole);
    }
}
