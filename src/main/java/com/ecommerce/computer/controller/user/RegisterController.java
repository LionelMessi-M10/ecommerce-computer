package com.ecommerce.computer.controller.user;

import com.ecommerce.computer.model.User;
import com.ecommerce.computer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String registerForm(Model model, User user){
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String registerAccount(@ModelAttribute("user") User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/login";
    }
}
