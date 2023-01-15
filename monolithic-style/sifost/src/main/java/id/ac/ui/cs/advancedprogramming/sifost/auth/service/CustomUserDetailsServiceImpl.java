package id.ac.ui.cs.advancedprogramming.sifost.auth.service;

import id.ac.ui.cs.advancedprogramming.sifost.auth.CustomUserDetails;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;

public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        var user = userRepository.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new CustomUserDetails(user);
    }

}