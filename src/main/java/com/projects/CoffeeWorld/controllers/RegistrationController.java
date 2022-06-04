package com.projects.CoffeeWorld.controllers;

import com.projects.CoffeeWorld.models.Role;
import com.projects.CoffeeWorld.models.User;
import com.projects.CoffeeWorld.repositories.UserRepository;
import com.projects.CoffeeWorld.services.EmailService;
import com.projects.CoffeeWorld.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) {
        User userFromDb = userRepository.findUserByUsername(user.getUsername());
        if (userFromDb != null) {
            model.addAttribute("message", "User exists, try to change username");
            return "registration";
        }
        List<User> usersFromDb = userRepository.findUserByEmail(user.getEmail());
        if (!usersFromDb.isEmpty()){
            model.addAttribute("message", "User exists, try to change email");
            return "registration";
        }
        user.setActivationCode(UUID.randomUUID().toString());
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Здравствуйте, %s! \n" +
                            "Пожалуйста, перейдите по ссылке, чтобы зарегистрироваться \n" +
                            "http://localhost:8081/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            emailService.send(user.getEmail(), "Activation Code", message);
        }
        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        model.addAttribute("messageOfLogin", "Пожалуйста, подтвердите свой аккаунт, перейдя по ссылке на почте");
        return "login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            model.addAttribute("messageOfLogin", "Поздравляем! Пользователь активирован!");
        } else {
            model.addAttribute("messageOfLogin", "Сожалеем, но пользователь с данным кодом не найден!");
        }
        return "login";
    }
}

