package id.ac.ui.cs.advancedprogramming.sifost.auth.Service;

import id.ac.ui.cs.advancedprogramming.sifost.auth.dto.UserDTO;
import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.Interest;
import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.Role;
import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.User;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.InterestRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.RoleRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.UserRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.service.AuthService;
import id.ac.ui.cs.advancedprogramming.sifost.auth.service.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest{
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private InterestRepository interestRepository;
    @InjectMocks
    private AuthService authService = new AuthServiceImpl();
    private User user;
    private Role role;
    private Interest interest;
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
        interest = new Interest("pendidikan");
        interest.setId((long)1);
        Set<Interest> interests = user.getInterest();
        interests.add(interest);
        user.setInterest(interests);
        user.setId(1L);
    }
    @Test
    void testRegisterUser(){
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        when(roleRepository.findByName("USER")).thenReturn(roles);
        when(userRepository.save(user)).thenReturn(user);
        when(interestRepository.getById((long)1)).thenReturn(interest);
        User temp = new User();
        temp.setUsername("richie");
        temp.setPassword("richie");
        temp.setId(1L);
        Long[] interestId = new Long[1];
        interestId[0] = (long)1;
        User res = authService.registerUser(temp,interestId);
        assertEquals(user.getUsername(),res.getUsername());

    }
    @Test
    void testEncodePassword(){
        String res = authService.encodePassword("richie");
        assertTrue(passwordEncoder.matches("richie",res));
    }

    @Test
    void testRegisterAdmin() {
        Role adminRole = new Role("ADMIN");
        List<Role> roles = new ArrayList<>();
        roles.add(adminRole);

        UserDTO dto = new UserDTO();
        dto.setUsername("admin");
        dto.setPassword("admin");
        dto.setEmail("admin@email.com");

        User mockAdmin = new User();
        mockAdmin.setUsername("admin");
        mockAdmin.setPassword("admin");
        mockAdmin.setEmail("admin@email.com");

        when(roleRepository.findByName("ADMIN")).thenReturn(roles);
        when(userRepository.save(mockAdmin)).thenReturn(mockAdmin);

        User admin = authService.registerAdmin(dto);
        assertEquals(mockAdmin.getUsername(), admin.getUsername());
        assertEquals(mockAdmin.getPassword(), admin.getPassword());
        assertEquals(mockAdmin.getEmail(), admin.getEmail());
    }
}