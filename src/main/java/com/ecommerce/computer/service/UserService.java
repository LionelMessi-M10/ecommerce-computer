package com.ecommerce.computer.service;

import com.ecommerce.computer.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    Page<User> findAll(Integer pageNo);
    List<User> findAll();
    User findByUserName(String username);
    User findById(Long id);
    void saveUser(User user);

}
