package it.uniroma3.siw.dotboard_backend.repository;

import it.uniroma3.siw.dotboard_backend.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

}

