package it.uniroma3.siw.dotboard_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @CreationTimestamp
    private Date createdAt;

    @Version
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(columnDefinition = "INTEGER DEFAULT 1")
    private Integer version;


    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @UpdateTimestamp
    @Nullable
    private Date updatedAt;
    @Nullable
    @JsonIgnore
    private Date deletedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date created_at) {
        this.createdAt = created_at;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Nullable
    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(@Nullable Date updated_at) {
        this.updatedAt = updated_at;
    }

    @Nullable
    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(@Nullable Date deleted_at) {
        this.deletedAt = deleted_at;
    }

}
