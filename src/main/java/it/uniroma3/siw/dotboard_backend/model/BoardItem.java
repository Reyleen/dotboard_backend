package it.uniroma3.siw.dotboard_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.uniroma3.siw.dotboard_backend.utils.ItemType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class BoardItem {

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

    @Nullable
    private String caption;

    @Nullable
    private Integer h;

    @Nullable
    private Integer w;

    @Nullable
    private Integer x;

    @Nullable
    private Integer y;

    @JsonIgnore
    @ManyToOne
    private Board board;

    @JsonIgnore
    @ManyToOne
    private Api api;

    private ItemType category;

    @Nullable
    private String title;

    @Nullable
    private String subtitle;

    @Nullable
    private String url;

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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

    public void setUpdatedAt(@Nullable Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Nullable
    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(@Nullable Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Nullable
    public String getCaption() {
        return caption;
    }

    public void setCaption(@Nullable String caption) {
        this.caption = caption;
    }

    @Nullable
    public Integer getH() {
        return h;
    }

    public void setH(@Nullable Integer height) {
        this.h = height;
    }

    @Nullable
    public Integer getW() {
        return w;
    }

    public void setW(@Nullable Integer width) {
        this.w = width;
    }


    public Api getApi() {
        return api;
    }

    public void setApi(Api api) {
        this.api = api;
    }

    @Nullable
    public Integer getX() {
        return x;
    }

    public void setX(@Nullable Integer x) {
        this.x = x;
    }

    @Nullable
    public Integer getY() {
        return y;
    }

    public void setY(@Nullable Integer y) {
        this.y = y;
    }

    public ItemType getCategory() {
        return category;
    }

    public void setCategory(ItemType category) {
        this.category = category;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    @Nullable
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(@Nullable String subtitle) {
        this.subtitle = subtitle;
    }

    @Nullable
    public String getUrl() {
        return url;
    }

    public void setUrl(@Nullable String url) {
        this.url = url;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BoardItem other = (BoardItem) obj;
        return Objects.equals(id, other.getId());
    }
}
