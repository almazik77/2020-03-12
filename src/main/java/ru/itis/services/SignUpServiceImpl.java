package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.itis.dto.SignUpDto;
import ru.itis.models.Mail;
import ru.itis.models.Role;
import ru.itis.models.State;
import ru.itis.models.User;
import ru.itis.repositories.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ExecutorService executorService;

    @Override
    public void signUp(SignUpDto form) {
        String rawPassword = form.getPassword();
        String hashPassword = passwordEncoder.encode(rawPassword);

        User user = User.builder()
                .email(form.getEmail())
                .name(form.getName())
                .hashPassword(hashPassword)
                .confirmString(UUID.randomUUID().toString())
                .state(State.NOT_CONFIRMED)
                .role(Role.USER)
                .build();

        userRepository.save(user);


        executorService.submit(() -> {
            Map<String, String> model = new HashMap<>();

            model.put("name", user.getName());
            model.put("link", "http://localhost:8080/confirm/" + user.getConfirmString());

            Mail mail = Mail.builder()
                    .subject("Registration")
                    .to(form.getEmail())
                    .model(model)
                    .from("musin.almaz33@gmail.com")
                    .build();

            mailService.sendConfirmEmail(mail);
        });

    }

    @Override
    public void confirm(String confirmCode) {
        userRepository.deleteConfirmString(confirmCode);
    }
}
