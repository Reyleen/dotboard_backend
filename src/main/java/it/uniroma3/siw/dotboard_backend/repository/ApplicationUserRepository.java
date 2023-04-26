package it.uniroma3.siw.dotboard_backend.repository;

import it.uniroma3.siw.dotboard_backend.model.ApplicationUser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

@Repository
public class ApplicationUserRepository {
    EntityManager em = null;
    Class<ApplicationUser> domainClass = null;

    public ApplicationUserRepository(Class<ApplicationUser> domainClass){
        this.domainClass=domainClass;
    }

    public void setEntityManager(EntityManager em){
        this.em = em;
    }
    public List<ApplicationUser> findAll() {
        return em.createQuery("select u from ApplicationUser u",ApplicationUser.class).getResultList();
    }

    public Optional<ApplicationUser> findById(Long id) {
        return Optional.ofNullable(em.find(ApplicationUser.class, id));
    }

    public ApplicationUser save(ApplicationUser user) {
        Method getId = null;
        ApplicationUser p = null;
        try {
            getId = this.domainClass.getMethod("getId");
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        try{
            if(getId.invoke(user)==null) {
                this.em.persist(user);
                p=user;
            }
            else{
                p=em.merge(user);
            }
        }
        catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
            e.printStackTrace();
        }
        return p;
    }

}

