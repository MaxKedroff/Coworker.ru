package com.example.Coworker.ru.authenticationService.config.service;

import com.example.Coworker.ru.authenticationService.config.entity.User;
import com.example.Coworker.ru.authenticationService.config.entity.UserDTO;
import com.example.Coworker.ru.authenticationService.config.jwt.JwtAuthentication;
import com.example.Coworker.ru.authenticationService.config.jwt.JwtProvider;
import com.example.Coworker.ru.authenticationService.config.jwt.JwtRequest;
import com.example.Coworker.ru.authenticationService.config.jwt.JwtResponse;
import com.example.Coworker.ru.authenticationService.config.repository.UserRepo;
import io.jsonwebtoken.Claims;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    private final JavaMailSender mailSender;

    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;

    @Autowired
    public UserService(JavaMailSender mailSender, JwtProvider jwtProvider) {
        this.mailSender = mailSender;
        this.jwtProvider = jwtProvider;
    }


    public JwtResponse login(@NonNull JwtRequest authRequest) throws AuthException {
        final User user = (User) loadUserByUsername(authRequest.getEmail());
        System.out.println(user.getUsername());
        if (user.isActive()){
            if (new BCryptPasswordEncoder().matches(authRequest.getPassword(), user.getPassword())) {
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String refreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getUsername(), refreshToken);
                return new JwtResponse(accessToken, refreshToken);
            } else {
                throw new AuthException("Неправильный пароль");
            }
        }
        throw new AuthException("почта не найдена, или не активирована, пожалуйста проверьте почту на наличие письма");
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = (User) loadUserByUsername(login);
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = (User) loadUserByUsername(login);
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getUsername(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public String create(UserDTO userDTO) throws MessagingException {

        if (!userDTO.getEmail().endsWith("@urfu.me")){
            return "мы работает только с корпоративными почтами урфу, заканчивающимися на @urfu.me";
        }
        if (userRepo.findByUsername(userDTO.getEmail()) != null){
            return "похоже, что аккаунт уже существует";
        }
        String verificationCode = generateVerificationCode();
        User user = User.builder()
                .username(userDTO.getEmail())
                .password(new BCryptPasswordEncoder().encode(userDTO.getPassword()))
                .authorities("student")
                .fullName(userDTO.getFull_name())
                .verificationCode(verificationCode)
                .build();
        userRepo.save(user);
        sendConfirmationEmail(user);
        return "Create Successfully!";
    }

    private String generateVerificationCode(){
        return RandomStringUtils.randomAlphanumeric(64);
    }

    private void sendConfirmationEmail(User user) throws MessagingException {
        String toAddress = user.getUsername();
        String subject = "Please verify your registration";
        String content = createEmailContent(user);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }
    private String createEmailContent(User user) {
        String verifyURL = "http://localhost:8070/api/auth/verify?code=" + user.getVerificationCode();
        return String.format(
                "Dear %s,\n\n" +
                        "Thank you for registering.\n" +
                        "Please click on the following link to verify your account:\n" +
                        "<a href='%s'>Verify Account</a>\n\n" +
                        "Best regards,\n" +
                        "Your Application",
                user.getUsername(), verifyURL
        );
    }
    public boolean handleVerification(String code) {
        Optional<User> optionalUser = userRepo.findByVerificationCode(code);
        boolean isVerified = false;
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setActive(true);
            user.setVerificationCode(null);
            userRepo.save(user);
            return true;
        }
        return false;
    }
    public boolean isActiveUser(String email){
        User user = userRepo.findByUsername(email);
        return user.isActive();
    }
}
