package it.uniroma3.siw.dotboard_backend.repository;

import javax.persistence.EntityManager;
import java.util.List;

public interface BaseRepository<T> {
    public  void setEntityManager(EntityManager em);
    public T save (T entity);
    public List<T> findAll();
    public T findById(Long id);
    public void delete(T t);
    public void deleteById(Long id);
    public T update(Long Id, T t);
}
