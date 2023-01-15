package id.ac.ui.cs.advancedprogramming.sifost.auth.controller;

import id.ac.ui.cs.advancedprogramming.sifost.auth.dto.UserDTO;
import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.User;
import id.ac.ui.cs.advancedprogramming.sifost.auth.service.AuthService;
import id.ac.ui.cs.advancedprogramming.sifost.auth.service.InterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AuthController {

    private static final String ERRORSTR = "error";

    @Autowired
    private AuthService authService;
    @Autowired
    private InterestService interestService;
    @GetMapping("")
    public String viewHomePage() {
        return "Auth/index";
    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {

        model.addAttribute("user", new UserDTO());
        model.addAttribute("interests",interestService.getAllInterest());
        model.addAttribute(ERRORSTR,"lolos");
        return "Auth/signup_form";
    }
    @PostMapping("/process_register")
    public String processRegister(UserDTO userDTO,
                                  @RequestParam(required = false,value = "interestId") Long[] interestId,Model model) {
        var user = new User();
        if(authService.existDuplicate(userDTO.getUsername())){
            model.addAttribute("user", new UserDTO());
            model.addAttribute("interests",interestService.getAllInterest());
            model.addAttribute(ERRORSTR,ERRORSTR);
            return "Auth/signup_form";
        }
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setDeskripsi(userDTO.getDeskripsi());
        user.setEmail(userDTO.getEmail());
        authService.registerUser(user,interestId);
        return "Auth/register_success";
    }

    @PostMapping("/login_success_handler")
    public String loginSuccessHandler() {
        return "redirect:/profile";

    }
}
