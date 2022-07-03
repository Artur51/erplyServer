package com.middleware.erply.services;

import com.middleware.erply.model.AuthRequest;
import com.middleware.erply.model.User;
import com.middleware.erply.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    @Override
    public UserDetails loadUserByUsername(
            String username) throws UsernameNotFoundException {
        return userRepository.findOneByUsername(username);
    }

    public User save(
            User user) {
        String password = user.getPassword();
        password = passwordEncoder.encode(password);
        user.setPassword(password);
        return userRepository.save(user);
    }

    public boolean isUserExists(
            User user) {
        return userRepository.findByUsername(user.getUsername()).isPresent();
    }

    public String login(
            AuthRequest request) {
        String password = request.getPassword();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(request.getUsername(), password);
        Authentication authenticate = authenticationManager.authenticate(authentication);

        User user = (User) authenticate.getPrincipal();

        return jwtTokenService.generate(user.getUsername());
    }
}
