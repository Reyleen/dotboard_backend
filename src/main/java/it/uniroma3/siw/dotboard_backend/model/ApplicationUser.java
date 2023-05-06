package it.uniroma3.siw.dotboard_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
public class ApplicationUser extends BaseModel {

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
  private List<Board> boards = new ArrayList<>();

  public List<Board> getBoards() {
    return boards;
  }

  public void setBoards(List<Board> boards) {
    this.boards = boards;
  }

  @ManyToMany(fetch = FetchType.LAZY)
  private Set<Role> roles = new HashSet<>();

  public ApplicationUser() {
  }

  public ApplicationUser(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
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

}

