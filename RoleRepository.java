package com.Online.ParkigManagement.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Online.ParkigManagement.model.AppRole;
import com.Online.ParkigManagement.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(AppRole roleDriver); 

    // Custom query methods can be defined here if needed
    // For example, to find roles by name or other criteria

    
}
    

