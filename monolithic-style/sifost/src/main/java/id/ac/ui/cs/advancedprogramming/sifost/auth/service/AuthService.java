package id.ac.ui.cs.advancedprogramming.sifost.auth.service;

import id.ac.ui.cs.advancedprogramming.sifost.auth.entity.User;

import java.util.List;

public interface AuthService {
    User registerUser(User user,Long[] minatId);
    List<User> getAllUser();
    String encodePassword(String password);
    boolean existDuplicate(String username);
}
