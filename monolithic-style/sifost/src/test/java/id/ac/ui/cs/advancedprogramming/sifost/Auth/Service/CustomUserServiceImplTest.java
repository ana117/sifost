package id.ac.ui.cs.advancedprogramming.sifost.Auth.Service;

import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.Role;
import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.User;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.UserRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.service.CustomUserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserServiceImplTest{

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private CustomUserDetailsServiceImpl customUserDetailsService = new CustomUserDetailsServiceImpl();
    private User user;
    private Role role;
    private BCryptPasswordEncoder passwordEncoder;
    @BeforeEach
    public void setUp(){

        String name = "USER";
        role = new Role(name);
        String username = "richie";
        String password = "richie";
        user = new User();
        user.setUsername(username);
        user.setPassword(password);
        passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        Set<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
    }
    @Test
    void testLoadUserByUsernameNotExist(){

        when(userRepository.getUserByUsername("richie")).thenReturn(null);
        Exception exception = assertThrows(UsernameNotFoundException.class,() -> {customUserDetailsService.loadUserByUsername("richie");});
        String expectedMessage = "Could not find user";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage,actualMessage);
    }
    @Test
    void testLoadUserByUsernameExist(){

        when(userRepository.getUserByUsername("richie")).thenReturn(user);
        UserDetails user = customUserDetailsService.loadUserByUsername("richie");
        assertEquals("richie",user.getUsername());
    }
}