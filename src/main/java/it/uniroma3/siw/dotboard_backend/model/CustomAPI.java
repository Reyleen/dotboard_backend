package it.uniroma3.siw.dotboard_backend.model;

import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@Entity
public class CustomAPI {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @PastOrPresent Date created_at;
    private Integer version;
    @Nullable
    private Date updated_at;
    @Nullable private Date deleted_at;
    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Nullable
    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(@Nullable Date updated_at) {
        this.updated_at = updated_at;
    }

    @Nullable
    public Date getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(@Nullable Date deleted_at) {
        this.deleted_at = deleted_at;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
