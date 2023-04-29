package it.uniroma3.siw.dotboard_backend.repository;

import it.uniroma3.siw.dotboard_backend.model.ApiClass;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Repository
public abstract class ApiRepository implements BaseRepository {
    EntityManager em;
    Class <ApiClass> domainClass;
    public void setEntityManager(EntityManager em) {
        this.em=em;
    }

    public ApiClass save(ApiClass entity) {
        Method getId = null;
        ApiClass api = null;
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

    public List<ApiClass> findAll() {
        return em.createQuery("select a from ApiClass a",this.domainClass).getResultList();
    }

    public ApiClass findById (Long id) {
        return  em.find(this.domainClass,id);
    }

    public void delete(ApiClass o) {
        em.remove(o);
    }

    public void deleteById(Long idA) {
        this.em.createQuery("delete from "+this.domainClass.getName()+"where id="+idA).executeUpdate();
    }

    public ApiClass update(Long id, ApiClass o) {
        ApiClass n = findById(id);
        n.setRef(o.getRef());
        n.setMethod(o.getMethod());
        n.setCreated_at(o.getCreated_at());
        n.setVersion(o.getVersion());
        n.setUpdated_at(o.getUpdated_at());
        n.setDeleted_at(o.getDeleted_at());
        return save(n);
    }
}
