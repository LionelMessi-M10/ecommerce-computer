package com.ecommerce.computer.controller.admin;

import com.ecommerce.computer.model.Role;
import com.ecommerce.computer.model.User;
import com.ecommerce.computer.service.RoleService;
import com.ecommerce.computer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping()
    public String adminHome(){
        return "admin/index";
    }

    @GetMapping("/user-list")
    public String userList(Model model, @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo){
        Page<User> userPage = userService.findAll(pageNo);

        model.addAttribute("totalPage", userPage.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("users", userPage);
        return "admin/user-list";
    }

    @GetMapping("/add-user")
    public String addUser(Model model){
        User user = new User();
        user.setId(0L);
        List<Role> roles = roleService.findAll();

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "admin/edit-user";
    }

    @GetMapping("/edit-user")
    public String editUser(@RequestParam("id") Long id, Model model){
        User user = userService.findById(id);
        List<Role> roles = roleService.findAll();

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "admin/edit-user";
    }
}
