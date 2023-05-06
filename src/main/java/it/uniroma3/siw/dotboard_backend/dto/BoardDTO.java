package it.uniroma3.siw.dotboard_backend.dto;

public class BoardDTO {
  private Long id;
  private String name;
  private String description;
  private String publicLink;

  public BoardDTO() {
  }

  public BoardDTO(Long id, String name, String description, String publicLink) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.publicLink = publicLink;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPublicLink() {
    return publicLink;
  }

  public void setPublicLink(String publicLink) {
    this.publicLink = publicLink;
  }
}