package mk.ukim.finki.wp.eshop.service;

import mk.ukim.finki.wp.eshop.model.User;
import mk.ukim.finki.wp.eshop.model.enumerations.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User register(String username, String password, String repeatPassword, String name, String surname, Role role);
}
