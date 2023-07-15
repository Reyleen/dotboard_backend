package it.uniroma3.siw.dotboard_backend.repository;

import it.uniroma3.siw.dotboard_backend.model.ApplicationUser;
import it.uniroma3.siw.dotboard_backend.utils.MergeUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long>, MergeUpdate {


  Optional<ApplicationUser> findByEmailAndDeletedAtIsNull(String email);

  Optional<ApplicationUser> findByIdAndDeletedAtIsNull(Long id);

  List<ApplicationUser> findByDeletedAtIsNull();

  Optional<ApplicationUser> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  Optional<ApplicationUser > findByUsernameAndDeletedAtIsNull(String username);

  default ApplicationUser create(ApplicationUser applicationUser) throws IllegalAccessException {

    // Check if user already exists
    ApplicationUser user = this.findByEmailAndDeletedAtIsNull(applicationUser.getEmail()).orElse(null);
    if (user != null) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
    }

    // Save user
    this.save(applicationUser);
    return this.findByEmailAndDeletedAtIsNull(applicationUser.getEmail())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
  }

  default ApplicationUser update(Long id, ApplicationUser applicationUser) throws IllegalAccessException {
    // Check if user exists
    ApplicationUser newApplicationUser = this.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

    // Merge data with existing user (only non-null fields)
    MergeUpdate.merge(newApplicationUser, applicationUser);

    // Update user
    this.save(newApplicationUser);
    return this.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
  }

}

