package com.ecommerce.computer.repository;

import com.ecommerce.computer.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
