package de.knoobie.project.fuko.database.domain.msc;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public @Getter @Setter abstract class MSCVGMdbMeta extends MSCLocalFolderStorage implements Serializable {

  @Basic
  @Column(nullable = true)
  private String vgmdbAdded;
  @Basic
  @Column(nullable = true)
  private String vgmdbUpdated;
  @Basic
  @Column(nullable = true)
  private String vgmdbLink;

  public MSCVGMdbMeta() {

  }

}
