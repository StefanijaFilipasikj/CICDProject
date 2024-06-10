package mk.ukim.finki.wp.eshop.service.impl;

import mk.ukim.finki.wp.eshop.model.User;
import mk.ukim.finki.wp.eshop.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.wp.eshop.model.exceptions.InvalidUserCredentialsException;
import mk.ukim.finki.wp.eshop.repository.UserRepository;
import mk.ukim.finki.wp.eshop.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User login(String username, String password) {
        if(username == null || password == null || username.isEmpty() || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        return this.userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(InvalidUserCredentialsException::new);
    }
}
