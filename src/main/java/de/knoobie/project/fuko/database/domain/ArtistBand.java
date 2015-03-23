package de.knoobie.project.fuko.database.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
public @Getter @Setter class ArtistBand extends Artist implements Serializable {

  @Basic
  @Column(nullable = true)
  private Date formed;

  @ManyToMany(targetEntity = ArtistPerson.class)
  private List<ArtistPerson> member;
  
  @ManyToMany(targetEntity = ArtistPerson.class)
  private List<ArtistPerson> formerMember;

  public ArtistBand() {

  }
  
  
}
