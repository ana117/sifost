package id.ac.ui.cs.advancedprogramming.sifost.profile.controller;

import id.ac.ui.cs.advancedprogramming.sifost.auth.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserProfileController {

    @GetMapping("/profile")
    public String userProfile(Authentication authentication, Model model){
        var customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        model.addAttribute("user",customUserDetails);
        return "Profile/profile";
    }
}
