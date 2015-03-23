package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.fuko.database.domain.msc.MSCLocalFileStorage;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
public @Getter @Setter class AlbumTrack extends MSCLocalFileStorage implements Serializable {

  @Basic
  @Column(nullable = true)
  private String trackLength;

  @ManyToMany(targetEntity = Name.class)
  private List<Name> names;
  
  @ManyToOne(optional = true, targetEntity = AlbumDisc.class)
  private AlbumDisc cd;
  

  public AlbumTrack() {

  }

  public String getTrackLength() {
    return trackLength;
  }

  public void setTrackLength(String trackLength) {
    this.trackLength = trackLength;
  }

  public List<Name> getNames() {
    return names;
  }

  public void setNames(List<Name> names) {
    this.names = names;
  }

}
