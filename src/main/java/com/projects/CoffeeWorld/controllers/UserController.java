package com.projects.CoffeeWorld.controllers;

import com.projects.CoffeeWorld.models.Role;
import com.projects.CoffeeWorld.models.User;
import com.projects.CoffeeWorld.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String userList(@AuthenticationPrincipal User user, Model model) {
        for (Role i : user.getRoles())
            if (i == Role.ADMIN) {
                model.addAttribute("users", userService.getUsers());
                return "userList";
            }
        return "sorry";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{user}")
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user) {
        userService.addUser(user, username, form);
        return "redirect:/user";
    }

    @GetMapping("/profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        return "userProfile";
    }

    @PostMapping("/profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) String newPassword,
            @RequestParam String oldPassword,
            @RequestParam(required = false) String username,
            Model model) {
        String messageP = userService.updateUser(user, newPassword, oldPassword, username);
        model.addAttribute("messageP", messageP);
        model.addAttribute("user", user);
        return "userProfile";
    }
}
