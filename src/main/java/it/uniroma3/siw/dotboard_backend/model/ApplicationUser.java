package it.uniroma3.siw.dotboard_backend.model;

import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.Date;
import java.util.Objects;

@Entity
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Nullable
    private String surname;
    private String email;
    private String passwordHash;
    @Nullable private Date birthDate;
    private Date created_at;
    private Integer version;
    @Nullable private Date updated_at;
    @Nullable private Date deleted_at;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationUser user = (ApplicationUser) o;
        return getId().equals(user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getSurname() {
        return surname;
    }

    public void setSurname(@Nullable String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Nullable
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(@Nullable Date birthDate) {
        this.birthDate = birthDate;
    }

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
}

