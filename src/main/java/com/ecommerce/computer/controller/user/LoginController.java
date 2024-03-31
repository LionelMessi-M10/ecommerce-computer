package com.ecommerce.computer.controller.user;

import com.ecommerce.computer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

//    @PostMapping("/login")
//    public String checkLogin(@ModelAttribute("user") User user, Model model){
//
//        User user1 = userService.findByUserName(user.getUsername());
//
//        if(user1 != null && passwordEncoder.matches(user.getPassword(), user1.getPassword())){
//            return "redirect:/home";
//        }
//
//        return "redirect:/login?error";
//    }

    @GetMapping("/showPage403")
    public String errorPage(){
        return "403-Page";
    }
}
