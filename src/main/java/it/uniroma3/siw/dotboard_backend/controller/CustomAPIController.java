package it.uniroma3.siw.dotboard_backend.controller;

import io.swagger.annotations.Api;
import it.uniroma3.siw.dotboard_backend.model.CustomAPI;
import it.uniroma3.siw.dotboard_backend.repository.CustomAPIRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@Api(tags="CustomApi")
@RequestMapping("/custom")
public class CustomAPIController {
    CustomAPIRepository customAPIRepository;

    @RequestMapping (value="/", method= RequestMethod.GET )
    public List<CustomAPI> getAll(){
        return customAPIRepository.findAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public CustomAPI getOne(@PathVariable("id") Long id) {
        return this.customAPIRepository.findById(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public CustomAPI create(@RequestBody CustomAPI api) {
        return customAPIRepository.save(api);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public CustomAPI update(@PathVariable("id") Long id, @RequestBody CustomAPI api) {
        return customAPIRepository.update(id, api);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {
        customAPIRepository.delete(id);
    }

}
