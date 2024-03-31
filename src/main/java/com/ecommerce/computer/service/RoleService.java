package com.ecommerce.computer.service;

import com.ecommerce.computer.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {

    List<Role> findAll();

    Role findById(Long id);
}
