package it.uniroma3.siw.dotboard_backend.model;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@MappedSuperclass
public class BaseModel {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  @CreationTimestamp
  private Date createdAt;
  private Integer version;
  @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  @CreationTimestamp
  @Nullable
  private Date updatedAt;
  @Nullable
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
