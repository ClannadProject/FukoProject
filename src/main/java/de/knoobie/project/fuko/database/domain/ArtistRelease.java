package de.knoobie.project.fuko.database.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
public @Getter @Setter class ArtistRelease extends Album implements Serializable {

  @Basic
  @Column(nullable = true)
  // parse List<String> roles to roles ', '
  private String roles;

  public ArtistRelease() {

  }
 
}
