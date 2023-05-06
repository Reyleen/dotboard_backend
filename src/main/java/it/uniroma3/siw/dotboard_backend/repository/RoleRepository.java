package it.uniroma3.siw.dotboard_backend.repository;


import it.uniroma3.siw.dotboard_backend.utils.ERole;
import it.uniroma3.siw.dotboard_backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
