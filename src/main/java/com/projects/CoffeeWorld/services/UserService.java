package com.projects.CoffeeWorld.services;

import com.projects.CoffeeWorld.models.Role;
import com.projects.CoffeeWorld.models.User;
import com.projects.CoffeeWorld.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public boolean activateUser(String code) {
        User user = userRepository.findUserByActivationCode(code);
        if (user == null)
            return false;
        user.setActivationCode(code);
        user.setActive(true);
        userRepository.save(user);
        return true;
    }

    public void addUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }

    public String updateUser(User user, String newPassword, String oldPassword, String username) {
        if (user.getPassword().equals(oldPassword)) {
            if (!username.isEmpty())
                if (userRepository.findUserByUsername(username) == null || user.getUsername().equals(username))
                    user.setUsername(username);
                else return "Данный username уже используется";
            if (!newPassword.isEmpty())
                user.setPassword(newPassword);
            userRepository.save(user);
            return "Данные успешно изменены";
        }
        return "Неверный пароль, попробуйте еще раз";
    }


//    public void deleteUser(Long id){
//        userRepository.deleteById(id);
//    }
}
