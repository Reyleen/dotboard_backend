package it.uniroma3.siw.dotboard_backend.model;

import org.springframework.lang.Nullable;
import javax.persistence.Entity;

@Entity
public class Board  extends  BaseModel{

    String name;
    String description;
    String publicLink;

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
