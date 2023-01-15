package id.ac.ui.cs.advancedprogramming.sifost.Auth.Controller;

import id.ac.ui.cs.advancedprogramming.sifost.auth.controller.AuthController;
import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.User;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.InterestRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.RoleRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.repository.UserRepository;
import id.ac.ui.cs.advancedprogramming.sifost.auth.service.AuthService;
import id.ac.ui.cs.advancedprogramming.sifost.auth.service.InterestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthController.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthService authService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleRepository roleRepository;
    @MockBean
    private InterestRepository interestRepository;
    @MockBean
    private InterestService interestService;
    @Test
    void whenGetURLShouldCallIndexPage() throws Exception{
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect((handler().methodName("viewHomePage")))
                .andExpect(view().name("Auth/index"));
    }

    @Test
    void whenGetRegisterURLShouldCallSignUpForm() throws Exception{
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect((handler().methodName("showRegistrationForm")))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("interests"))
                .andExpect(view().name("Auth/signup_form"));
    }
    @Test
    @WithMockUser
    void whenPostLoginSuccessHandlerURLShouldCallSignUpForm() throws Exception{
        var TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        var httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        var csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
        mockMvc.perform(post("/login_success_handler").sessionAttr(TOKEN_ATTR_NAME,csrfToken)
                        .param(csrfToken.getParameterName(),csrfToken.getToken()))
                .andExpect((handler().methodName("loginSuccessHandler")));

    }
    @Test
    void whenPostProcessRegisterURLShouldCallAuthService() throws Exception{
        var TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        var httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        var csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
        var user = new User();
        user.setUsername("richie");
        var interestId = new Long[1];
        interestId[0] = (long)1;
        when(authService.existDuplicate("richie")).thenReturn(true);
        mockMvc.perform(post("/process_register").sessionAttr(TOKEN_ATTR_NAME,csrfToken)
                        .param(csrfToken.getParameterName(),csrfToken.getToken())
                        .param("username","richie")
                        .param("password","richie")
                        .param("deskripsi","richie")
                        .param("email","richie")
                        .param("interestId","1"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("interests"))
                .andExpect(model().attributeExists("error"))
                .andExpect(view().name("Auth/signup_form"))
                .andExpect((handler().methodName("processRegister")));
            verify(authService,times(1)).existDuplicate("richie");
    }
    @Test
    void whenPostProcessRegisterURLSameUsernameShouldReturnSignUp() throws Exception{
        var TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        var httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        var csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
        var user = new User();
        when(authService.existDuplicate("richie")).thenReturn(false);
        user.setUsername("richie");
        var interestId = new Long[1];
        interestId[0] = (long)1;
        mockMvc.perform(post("/process_register").sessionAttr(TOKEN_ATTR_NAME,csrfToken)
                        .param(csrfToken.getParameterName(),csrfToken.getToken())
                        .param("username","richie")
                        .param("password","richie")
                        .param("deskripsi","richie")
                        .param("email","richie")
                        .param("interestId","1"))
                .andExpect((handler().methodName("processRegister")));
        verify(authService,times(1)).registerUser(user,interestId);
    }
}
