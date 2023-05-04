package it.uniroma3.siw.dotboard_backend.controller;

import io.swagger.annotations.Api;
import io.swagger.models.Model;
import it.uniroma3.siw.dotboard_backend.model.ApplicationUser;
import it.uniroma3.siw.dotboard_backend.repository.ApplicationUserRepository;
import it.uniroma3.siw.dotboard_backend.services.Sanitizer;
import it.uniroma3.siw.dotboard_backend.services.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/users")
@Api(tags = "Application User")
public class ApplicationUserController implements Validator {

  @Autowired
  private ApplicationUserRepository applicationUserRepository;

  // GET /users/{id}
  @RequestMapping(value = "{id}", method = RequestMethod.GET)
  public ApplicationUser getById(@PathVariable("id") Long id) {
    return this.applicationUserRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
  }

  // GET /users
  @RequestMapping(value = "", method = RequestMethod.GET)
  public Collection<ApplicationUser> getAll() {
    return this.applicationUserRepository.findByDeletedAtIsNull();
  }


  // POST /users
  @RequestMapping(value = "", method = RequestMethod.POST)
  public ApplicationUser create(@RequestBody @Valid ApplicationUser applicationUser,
                                BindingResult bindingResult) throws IllegalAccessException {

    // Validate input data
    Validator.validate(bindingResult);
    return this.applicationUserRepository.create(applicationUser);
  }

  // PUT /users/{id}
  @RequestMapping(value = "{id}", method = RequestMethod.PUT)
  public ApplicationUser update(@PathVariable("id") Long id,
                                @RequestBody @Valid ApplicationUser applicationUser,
                                BindingResult bindingResult) throws IllegalAccessException {
    this.applicationUserRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

    // Validate input data
    Validator.validate(bindingResult);
    return this.applicationUserRepository.update(id, applicationUser);
  }

  // DELETE /users/{id}
  @RequestMapping(value="{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable("id") Long id) throws IllegalAccessException {
    ApplicationUser applicationUser = this.applicationUserRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    if(applicationUser.getDeletedAt() == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    applicationUser.setDeletedAt(new Date());
    this.applicationUserRepository.update(id, applicationUser);
  }

}
