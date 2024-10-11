package com.example.Coworker.ru.authenticationService.config.service;

import com.example.Coworker.ru.authenticationService.config.entity.User;
import com.example.Coworker.ru.authenticationService.config.entity.UserDTO;
import com.example.Coworker.ru.authenticationService.config.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public String create(UserDTO userDTO){

        if (!userDTO.getEmail().endsWith("@urfu.me")){
            return "sorry, we only work with @urfu.me mails, this is private system";
        }
        User user = User.builder()
                .username(userDTO.getEmail())
                .password(new BCryptPasswordEncoder().encode(userDTO.getPassword()))
                .authorities("student")
                .build();
        userRepo.save(user);
        return "Create Successfully!";
    }
}
