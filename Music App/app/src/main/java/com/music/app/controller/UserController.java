package com.music.app.controller;

import com.music.app.model.User;
import com.music.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")  //working
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")  //working
    public String login(@ModelAttribute User user, Model model) {
        User existingUser = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if (existingUser != null) {
            return "redirect:/api/songs/home";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "redirect:/api/users/register";
        }
    }

    @GetMapping("/register")  //working
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")  //working
    public String register(@ModelAttribute User user, Model model) {
        User existingUser = userService.findByEmail(user.getEmail());
        if (existingUser != null) {
            System.out.println("Email already registered");
            return "redirect:/api/users/register";
        } else {
            userService.save(user);
            System.out.println("Email added");
            return "redirect:/api/users/login";
        }
    }

    @GetMapping("/logout")  //working
    public String logout() {
        return "redirect:/login";
    }
}
