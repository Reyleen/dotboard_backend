package it.uniroma3.siw.dotboard_backend.repository;

import javax.persistence.EntityManager;
import java.util.List;

public interface BaseRepository<Object> {
    public  void setEntityManager(EntityManager em);
    public Object save (Object entity);
    public List<Object> findAll();
    public Object findById(Long id);
    public void delete(Long id);
    public Object update(Long Id, Object t);
}
