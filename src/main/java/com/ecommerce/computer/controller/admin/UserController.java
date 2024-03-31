package com.ecommerce.computer.controller.admin;

import com.ecommerce.computer.model.Role;
import com.ecommerce.computer.model.User;
import com.ecommerce.computer.service.RoleService;
import com.ecommerce.computer.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/add-user")
    public String addUser(Model model, @ModelAttribute("user") User user, HttpServletRequest httpServletRequest){

        Long roleId = Long.parseLong(httpServletRequest.getParameter("role"));
        Integer enabled = Integer.parseInt(httpServletRequest.getParameter("enabled"));
        Role role = roleService.findById(roleId);

        List<Role> roles = new ArrayList<>();
        roles.add(role);

        if(user.getId() != 0){
            user.setRoles(roles);
            user.setEnabled(enabled);
            userService.saveUser(user);
            model.addAttribute("role_id", roles.get(0).getId());
        }else{
            user.setRoles(roles);
            user.setEnabled(1);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            model.addAttribute("role_id", roles.get(0).getId());
        }

        userService.saveUser(user);

        return "redirect:/admin/user-list";
    }


}
