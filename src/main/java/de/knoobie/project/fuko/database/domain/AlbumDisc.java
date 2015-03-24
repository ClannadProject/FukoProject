package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.fuko.database.domain.msc.MSCLocalFolderStorage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
public @Getter @Setter class AlbumDisc extends MSCLocalFolderStorage implements Serializable {
  
  @Basic
  @Column(nullable = true)
  private String discLengh;
  @Basic
  @Column(nullable = true)
  private String name;
  
  @ManyToOne(optional = true, targetEntity = Album.class)
  private Album album;  

  @OneToMany(targetEntity = AlbumTrack.class, mappedBy = "cd")
  private List<AlbumTrack> tracks = new ArrayList<>();

  public AlbumDisc() {

  }
  
}
