package it.uniroma3.siw.dotboard_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
public class ApplicationUser{

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

  private String name;
  private String surname;
  @Email
  private String email;
  @Size(max = 20)
  private String username;
  @JsonIgnore
  private String password;
  @Past
  private Date birthDate;

  @OneToMany
  private List<Board> boards;

  @ManyToMany(fetch = FetchType.LAZY)
  private Set<Role> roles;

    public ApplicationUser() {
        roles = new HashSet<>();
        boards = new ArrayList<>();
    }

  public ApplicationUser(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
    roles = new HashSet<>();
    boards = new ArrayList<>();
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

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String passwordHash) {
    this.password = passwordHash;
  }

  @Nullable
  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(@Nullable Date birthDate) {
    this.birthDate = birthDate;
  }

  public List<Board> getBoards() {
    return boards;
  }

  public void setBoards(List<Board> boards) {
    this.boards = boards;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

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
}

