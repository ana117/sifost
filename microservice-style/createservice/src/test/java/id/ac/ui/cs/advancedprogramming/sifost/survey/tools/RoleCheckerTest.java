package id.ac.ui.cs.advancedprogramming.sifost.survey.tools;

import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.Role;
import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.User;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleCheckerTest {

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    RoleChecker roleChecker = new RoleChecker();

    @Test
    void testIsAdminTrue() {
        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        List<Role> adminRoles = new ArrayList<>();
        adminRoles.add(adminRole);

        User user = new User();
        Set<Role> roles = new HashSet<>(adminRoles);
        user.setRoles(roles);

        when(roleRepository.findByName("ADMIN")).thenReturn(adminRoles);
        boolean result = roleChecker.isAdmin(user);
        assertTrue(result);
    }

    @Test
    void testIsAdminFalse() {
        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        List<Role> adminRoles = new ArrayList<>();
        adminRoles.add(adminRole);

        User user = new User();
        Set<Role> roles = new HashSet<>();
        user.setRoles(roles);

        when(roleRepository.findByName("ADMIN")).thenReturn(adminRoles);
        boolean result = roleChecker.isAdmin(user);
        assertFalse(result);
    }
}