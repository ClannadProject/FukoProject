package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.nagisa.gson.model.bo.enums.VGMdbGender;
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
public @Getter @Setter class ArtistPerson extends Artist implements Serializable {

// Person
  @Basic
  @Column(nullable = true)
  private Date birthdate;
  @Basic
  @Column(nullable = true)
  private String birthplace;
  @Basic
  @Column(nullable = true)
  private String bloodtype;
  @Basic
  @Column(nullable = true)
  private VGMdbGender gender;

  // info -> Units -> BandMemberOf
  @ManyToMany(targetEntity = Artist.class)
  private List<Artist> bandMemberOf;
  
  public ArtistPerson(){
    
  }
  
  
}
