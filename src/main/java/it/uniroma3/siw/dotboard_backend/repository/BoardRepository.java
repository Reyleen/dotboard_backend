package it.uniroma3.siw.dotboard_backend.repository;
import it.uniroma3.siw.dotboard_backend.model.Board;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Repository
public abstract class BoardRepository implements BaseRepository {
    EntityManager em;
    Class <Board> domainClass;

    public BoardRepository(Class<Board > domainClass){
        this.domainClass=domainClass;
    }

    public void setEntityManager(EntityManager em){
        this.em = em;
    }
    public List<Board> findAll() {
        return em.createQuery("select b from Board b",Board.class).getResultList();
    }

    public Board findById(Long id) {
        return em.find(Board.class, id);
    }

    public Board save(Board board) {
        Method getId = null;
        Board n = null;
        try {
            getId = this.domainClass.getMethod("getId");
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        try {
            if (getId.invoke(board) == null) {
                this.em.persist(board);
                n = board;
            } else {
                n = em.merge(board);
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return n;
    }

    public Board update(Long id, Board b){
       Board n = findById(id);
        n.setName(b.getName());
        n.setHeight(b.getHeight());
        n.setWidth(b.getWidth());
        n.setCreated_at(b.getCreated_at());
        n.setVersion(b.getVersion());
        n.setUpdated_at(b.getUpdated_at());
        n.setDeleted_at(b.getDeleted_at());
        return save(n);
    }

    public void deleteById(Long idUser){
        this.em.createQuery("delete from "+this.domainClass.getName()+"where id=idUser").executeUpdate();
    }

}
