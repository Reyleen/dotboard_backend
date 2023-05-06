package it.uniroma3.siw.dotboard_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.Nullable;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Board  extends  BaseModel{

    String name;
    String description;
    String publicLink;

    @JsonIgnore
    @OneToOne
    ApplicationUser user;

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    @Nullable
    public String getPublicLink() {
        return publicLink;
    }

    public void setPublicLink(@Nullable String publicLink) {
        this.publicLink = publicLink;
    }
}
