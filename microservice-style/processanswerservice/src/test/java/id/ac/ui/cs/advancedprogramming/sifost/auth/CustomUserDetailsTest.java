package id.ac.ui.cs.advancedprogramming.sifost.auth;

import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.Interest;
import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.Role;
import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CustomUserDetailsTest {

    CustomUserDetails customUserDetails;

    @BeforeEach
    public void setUp(){
        User user = new User();
        user.setUsername("richie");
        user.setPassword("richie");
        user.setEmail("richie");
        user.setDeskripsi("richie");
        Set<Role> temp = new HashSet();
        temp.add(new Role("USER"));
        user.setRoles(temp);
        Set<Interest> interests = user.getInterest();
        interests.add(new Interest("Komputer"));
        user.setInterest(interests);
        customUserDetails = new CustomUserDetails(user);

    }
    @Test
    void GetUserNameReturnCorrectValue(){
        assertEquals("richie",customUserDetails.getUsername());
    }
    @Test
    void GetPasswordReturnCorrectValue(){
        assertEquals("richie",customUserDetails.getPassword());
    }
    @Test
    void IsAccountNonExpiredReturnTrue(){
        assertEquals(true,customUserDetails.isAccountNonExpired());
    }
    @Test
    void IsAccoutNonLockedReturnTrue(){
        assertEquals(true,customUserDetails.isAccountNonLocked());
    }
    @Test
    void IsCredentialNonExpiredReturnTrue(){
        assertEquals(true,customUserDetails.isCredentialsNonExpired());
    }
    @Test
    void isEnabledReturnTrue(){
        assertEquals(true,customUserDetails.isEnabled());
    }
    @Test
    void authoritiesReturnCorrectAuthorities(){
        List<? extends GrantedAuthority> temp = new ArrayList(customUserDetails.getAuthorities());
        assertEquals("USER",temp.get(0).getAuthority());
    }
    @Test
    void getEmailReturnCorrectValue(){
        assertEquals("richie",customUserDetails.getEmail());
    }
    @Test
    void getDeskripsiReturnCorrectValue(){
        assertEquals("richie",customUserDetails.getDeskripsi());
    }
    @Test
    void getInterestsReturnCorrectValue(){
        Object[] interests = customUserDetails.getInterests().toArray();


        assertEquals("Komputer",((Interest)interests[0]).getName());
    }
}
