package it.uniroma3.siw.dotboard_backend.dto;

import java.util.Date;

public class ApplicationUserDTO {
  private Long id;
  private String name;
  private String surname;
  private String email;
  private String username;
  private Date birthDate;

  // Costruttori

  public ApplicationUserDTO() {
  }

  public ApplicationUserDTO(Long id, String name, String surname, String email, String username, Date birthDate) {
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.email = email;
    this.username = username;
    this.birthDate = birthDate;
  }

  // Metodi getter e setter

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

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }
}
