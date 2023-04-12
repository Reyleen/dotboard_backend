package it.uniroma3.siw.dotboard_backend.model;

import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Board {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Nullable
    private Date birthDate;
    private Date created_at;
    private Integer version;
    @Nullable private Date updated_at;
    @Nullable private Date deleted_at;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
