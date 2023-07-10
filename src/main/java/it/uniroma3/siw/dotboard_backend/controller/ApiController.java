package it.uniroma3.siw.dotboard_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.uniroma3.siw.dotboard_backend.model.Api;
import it.uniroma3.siw.dotboard_backend.model.BoardItem;
import it.uniroma3.siw.dotboard_backend.repository.ApiRepository;
import it.uniroma3.siw.dotboard_backend.services.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/apis")
@SecurityRequirement(name = "Bearer_JWT")
@Tag(name = "API", description = "The API. Has various functions and is placed inside the BoardItems.")
public class ApiController implements Validator {
    @Autowired
    private ApiRepository apiRepository;


    // GET all boardItems
    @Operation(summary = "Get all APIs of the system")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<Api> getAll() {
        return this.apiRepository.findAll();
    }

    @Operation(summary = "Get API by id")
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Api getById(@PathVariable("id") Long id) {
        return this.apiRepository.findById(id).orElse(null);
    }

    //Da modificare ed implementare Api
    @Operation(summary = "Create a new API")
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Api create(@RequestBody Api api) {
        //dobbiamo collegarlo ad una api
        return this.apiRepository.save(api);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete an API")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {
        Api api = this.apiRepository.findById(id).orElse(null);
        if (api == null) {
            return;
        }
        for(BoardItem b : api.getBoarditems()) {
            b.setApi(null);
        }
        api.setDeletedAt(new Date());
        this.apiRepository.save(api);
    }

}
