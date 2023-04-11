package it.uniroma3.siw.dotboard_backend.controller;

import java.util.List;
import java.util.Optional;

import it.uniroma3.siw.dotboard_backend.model.ApplicationUser;
import it.uniroma3.siw.dotboard_backend.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api")
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @RequestMapping(value="/users", method=RequestMethod.GET)
    public List<ApplicationUser> getAll() {
        return applicationUserRepository.findAll();
    }

    @RequestMapping(value="/users/{userId}", method=RequestMethod.GET)
    public ApplicationUser getOne(@PathVariable("userId") Long userId) {
        if (userId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST
            );
        }
        ApplicationUser user = applicationUserRepository.findById(userId).orElse(null);
        if(user == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        return user;
    }

    @RequestMapping(value="/users", method=RequestMethod.POST)
    public ApplicationUser create(ApplicationUser user) {
        return applicationUserRepository.save(user);
    }

    @RequestMapping(value="/users", method=RequestMethod.PUT)
    public ApplicationUser update(ApplicationUser user) {
        return applicationUserRepository.save(user);
    }

    @RequestMapping(value="/users", method=RequestMethod.DELETE)
    public void delete(ApplicationUser user) {
        applicationUserRepository.delete(user);
    }

}