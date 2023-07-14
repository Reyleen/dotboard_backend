package it.uniroma3.siw.dotboard_backend.services;

import it.uniroma3.siw.dotboard_backend.model.Api;
import it.uniroma3.siw.dotboard_backend.model.BoardItem;
import it.uniroma3.siw.dotboard_backend.repository.ApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;

@Service
public class ApiService {

    @Autowired
    private ApiRepository apiRepository;

    @Transactional
    public Iterable<Api> getAll() {
        return this.apiRepository.findAll();
    }

    @Transactional
    public Api getById(@PathVariable("id") Long id) {
        return this.apiRepository.findByIdAndDeletedAtIsNull(id);
    }

    @Transactional
    public Api create(@RequestBody Api api) {
        return this.apiRepository.save(api);
    }

    @Transactional
    public Api delete(@PathVariable("id") Long id) {
        Api api = this.getById(id);
        for(BoardItem b : api.getBoarditems()) {
            b.setApi(null);
        }
        api.setDeletedAt(new Date());
        return api;
    }
}
