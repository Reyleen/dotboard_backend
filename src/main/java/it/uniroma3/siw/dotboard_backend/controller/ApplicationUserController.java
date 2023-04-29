package it.uniroma3.siw.dotboard_backend.controller;

import io.swagger.annotations.Api;
import it.uniroma3.siw.dotboard_backend.model.ApplicationUser;
import it.uniroma3.siw.dotboard_backend.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
@Api(tags = "Application User")
public class ApplicationUserController {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<ApplicationUser> getAll() {
        return applicationUserRepository.findAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ApplicationUser getOne(@PathVariable("id") Long id) {
        return this.applicationUserRepository.findById(id);//orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ApplicationUser create(@RequestBody ApplicationUser user) {
        return applicationUserRepository.save(user);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ApplicationUser update(@PathVariable("id") Long id, @RequestBody ApplicationUser user) {
            return applicationUserRepository.update(id, user); //new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {
        applicationUserRepository.deleteById(id);
    }
}
