package com.example.demo.service.login;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.login.LoginUserRepository;
import com.example.demo.app.login.LoginUserDetails;

@Service
public class LoginUserDetailsService implements UserDetailsService {

    private final LoginUserRepository loginUserRepository;

    public LoginUserDetailsService(LoginUserRepository loginUserRepository) {
        this.loginUserRepository = loginUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> loginUserOptional = loginUserRepository.findByEmail(email);
        return loginUserOptional.map(user -> new LoginUserDetails(user))
                .orElseThrow(() -> new UsernameNotFoundException("not found"));
    }
}
