package com.shujaat.blogs.services.implementations;

import com.shujaat.blogs.entities.Role;
import com.shujaat.blogs.entities.User;
import com.shujaat.blogs.exceptions.BlogAPIException;
import com.shujaat.blogs.payloads.LoginDto;
import com.shujaat.blogs.payloads.RegisterUserDto;
import com.shujaat.blogs.respositories.RoleRepository;
import com.shujaat.blogs.respositories.UserRepository;
import com.shujaat.blogs.services.interfaces.IAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService implements IAuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository,
                       RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(LoginDto loginDto) {
      Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      loginDto.getUsernameOrEmail(),
                      loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "User logged-in successfully!.";
    }

    @Override
    public String register(@RequestBody RegisterUserDto registerUserDto) {
        if(userRepository.existsByUsername(registerUserDto.getUsername()))
        {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username is taken!.");
        }

        if(userRepository.existsByEmail(registerUserDto.getEmail())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email Id already exists!.");
        }

        User user = new User();
        user.setEmail(registerUserDto.getEmail());
        user.setUsername(registerUserDto.getUsername());
        user.setName(registerUserDto.getName());
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);

        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully.";
    }
}
