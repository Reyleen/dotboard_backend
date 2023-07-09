package it.uniroma3.siw.dotboard_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.uniroma3.siw.dotboard_backend.model.ApplicationUser;
import it.uniroma3.siw.dotboard_backend.repository.ApplicationUserRepository;
import it.uniroma3.siw.dotboard_backend.repository.RoleRepository;
import it.uniroma3.siw.dotboard_backend.services.Validator;
import it.uniroma3.siw.dotboard_backend.utils.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.Date;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "Bearer_JWT")
@Tag(name = "User", description = "The User API. Contains all the operations that can be performed on a user.")
public class ApplicationUserController implements Validator {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    // GET /users/{id}
    @Operation(summary = "Get user by id")
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ApplicationUser getById(@PathVariable("id") Long id) {
        return this.applicationUserRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    // GET /users
    @Operation(summary = "Get all users")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Collection<ApplicationUser> getAll() {
        return this.applicationUserRepository.findByDeletedAtIsNull();
    }


    // POST /users
    @Operation(summary = "Create a new user")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ApplicationUser create(@RequestBody @Valid ApplicationUser applicationUser,
                                  BindingResult bindingResult) throws IllegalAccessException {
        // Validate input data
        Validator.validate(bindingResult);
        return this.applicationUserRepository.create(applicationUser);
    }

    // PUT /users/{id}
    @Operation(summary = "Update an existing user")
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ApplicationUser update(@PathVariable("id") Long id,
                                  @RequestBody @Valid ApplicationUser applicationUser,
                                  BindingResult bindingResult) throws IllegalAccessException {
        this.applicationUserRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Validate input data
        Validator.validate(bindingResult);
        return this.applicationUserRepository.update(id, applicationUser);
    }

    // DELETE /users/{id}
    @Operation(summary = "Delete an existing user")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) throws IllegalAccessException {
        ApplicationUser applicationUser = this.applicationUserRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        applicationUser.setDeletedAt(new Date());
        this.applicationUserRepository.update(id, applicationUser);
    }

    @Operation(summary = "Get user by username")
    @RequestMapping(value = "/findByUsername/{username}", method = RequestMethod.GET)
    public ApplicationUser getByUsername(@PathVariable("username") String username, Principal principal) {
        ApplicationUser current = this.applicationUserRepository.findByUsername(principal.getName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        ApplicationUser requested = this.applicationUserRepository.findByUsernameAndDeletedAtIsNull(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        if (current.getId().equals(requested.getId()) || current.getRoles().contains(this.roleRepository.findByName(ERole.ROLE_ADMIN).orElse(null))){
            return requested;
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to access this resource");
        }
    }

}
