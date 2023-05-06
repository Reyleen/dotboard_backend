package it.uniroma3.siw.dotboard_backend.model;

import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
public class Board  extends  BaseModel{

    @NotBlank String name;
    @Nullable String description;
    Integer height; //Nullable?
    Integer width;
    @Nullable String publicLink;

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

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Nullable
    public String getPublicLink() {
        return publicLink;
    }

    public void setPublicLink(@Nullable String publicLink) {
        this.publicLink = publicLink;
    }
}
