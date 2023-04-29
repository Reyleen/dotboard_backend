package it.uniroma3.siw.dotboard_backend.repository;

import it.uniroma3.siw.dotboard_backend.model.ApplicationUser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Repository
public abstract class ApplicationUserRepository implements BaseRepository {
    EntityManager em;
    Class<ApplicationUser> domainClass;

    public ApplicationUserRepository(Class<ApplicationUser> domainClass){
        this.domainClass=domainClass;
    }

    public void setEntityManager(EntityManager em){
        this.em = em;
    }


    public List<ApplicationUser> findAll() {
        return em.createQuery("select o from "+this.domainClass.getName()+"o",this.domainClass).getResultList();
    }

    public ApplicationUser findById(Long id) {
        return em.find(this.domainClass, id);
    }

    public ApplicationUser save(ApplicationUser entity) {
        Method getId = null;
        ApplicationUser p = null;
        try {
            getId = this.domainClass.getMethod("getId");
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        try{
            if(getId.invoke(entity)==null) {
                this.em.persist(entity);
                p=entity;
            }
            else{
                p=em.merge(entity);
            }
        }
        catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
            e.printStackTrace();
        }
        return p;
    }

    public void delete (ApplicationUser u){
        this.em.remove(u);
    }

    public void deleteById(Long idUser){
        this.em.createQuery("delete from "+this.domainClass.getName()+"where id=idUser").executeUpdate();
    }

    public ApplicationUser update(Long id, ApplicationUser u){
        ApplicationUser n = findById(id);
        n.setName(u.getName());
        n.setSurname(u.getSurname());
        n.setEmail(u.getEmail());
        n.setPasswordHash(u.getPasswordHash());
        n.setBirthDate(u.getBirthDate());
        n.setCreated_at(u.getCreated_at());
        n.setVersion(u.getVersion());
        n.setUpdated_at(u.getUpdated_at());
        n.setDeleted_at(u.getDeleted_at());
        return save(n);
    }

}

