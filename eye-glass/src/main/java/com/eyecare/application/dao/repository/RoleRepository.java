package com.eyecare.application.dao.repository;

import com.eyecare.application.dao.userrole.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}