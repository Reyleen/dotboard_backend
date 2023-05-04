package it.uniroma3.siw.dotboard_backend.repository;

import it.uniroma3.siw.dotboard_backend.model.ApiClass;
import it.uniroma3.siw.dotboard_backend.model.CustomAPI;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

@Repository
public class CustomAPIRepository implements BaseRepository<CustomAPI> {

    EntityManager em;
    Class <CustomAPI> domainClass;

    public void setEntityManager(EntityManager em) {
        this.em=em;
    }

    public CustomAPI save(CustomAPI entity) {
        Method getId = null;
        CustomAPI api = null;
        try {
            getId = this.domainClass.getMethod("getId");
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        try {
            if (getId.invoke(entity) == null) {
                this.em.persist(entity);
                api = entity;
            } else {
                api = em.merge(entity);
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return api;
    }

    public List<CustomAPI> findAll() {
        return em.createQuery("select c from CustomAPI c",this.domainClass).getResultList();
    }

    public CustomAPI findById(Long id) {
        return  em.find(this.domainClass,id);
    }

    public void delete(Long id) {
        CustomAPI api= findById(id);
        Date dt = new Date();
        api.setDeleted_at(dt);
    }

    public CustomAPI update(Long Id, CustomAPI t) {
        return null;
    }
}
