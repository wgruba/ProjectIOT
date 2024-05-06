package com.example.springboot.Mail;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.Seciurity.JwtUtils;
import com.example.springboot.User.User;
import com.example.springboot.User.UserRepository;

@RestController
public class PasswordResetController {
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/unauthorized/sendResetToken")
    public Integer sendResetToken(@RequestBody String mail) {
        try {
            Optional<User> existingUser = userRepository.getUserByMail(mail);
            if (existingUser.isPresent()) {
                User user = existingUser.get();
                String token = jwtUtils.generateJwtToken(user);
                String subject = "Reset Password";
                String text = "Złożono żądanie o reset hasła do konta na IwentHub przypisanego do tego maila"
                        + "\n\nJeśli to nie ty złożyłeś to żądanie, zignoruj tą wiadomość"
                        + "\n\nJeśli to ty złożyłeś to żądanie, kliknij w poniższy link, aby zresetować hasło"
                        + "\n\nhttp://localhost:4200/resetPassword/" + token
                        + "\n\nPozdrawiamy, zespół IwentHub";
                emailService.sendEmail(mail, subject, text);
                return 0;
            }
            else {
                return 1;
            }
        } catch (Exception e) {
            return 2;
        }
    }

    @PutMapping("/unauthorized/resetPasswordConfirm/{token}")
    public Boolean resetPassword(@PathVariable String token, @RequestBody String password) {
        Date exp = jwtUtils.getExpirationDateFromToken(token);
        Date now = Calendar.getInstance().getTime();
        if (exp.after(now)) {
            String name = jwtUtils.getUserNameFromJwtToken(token);
            Optional<User> existingUser = userRepository.getUserByNameOrMail(name);
            if (existingUser.isPresent()) {
                User user = existingUser.get();
                user.setPassword(passwordEncoder.encode(password));
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

}
