package it.uniroma3.siw.dotboard_backend.controller;

import io.swagger.annotations.Api;
import it.uniroma3.siw.dotboard_backend.model.ApiClass;
import it.uniroma3.siw.dotboard_backend.repository.ApiRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/api")
@Api(tags = "Api")
public class ApiController {

    ApiRepository apiRepository;

    @RequestMapping (value="/", method= RequestMethod.GET )
    public List<ApiClass> getAll(){
        return apiRepository.findAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ApiClass getOne(@PathVariable("id") Long id) {
        return this.apiRepository.findById(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ApiClass create(@RequestBody ApiClass apiClass) {
        return apiRepository.save(apiClass);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ApiClass update(@PathVariable("id") Long id, @RequestBody ApiClass api) {
        return apiRepository.update(id, api);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {
        apiRepository.deleteById(id);
    }

}
