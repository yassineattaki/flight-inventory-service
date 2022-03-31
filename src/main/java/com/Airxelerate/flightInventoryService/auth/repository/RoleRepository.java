package com.Airxelerate.flightInventoryService.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Airxelerate.flightInventoryService.auth.model.ERole;
import com.Airxelerate.flightInventoryService.auth.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}