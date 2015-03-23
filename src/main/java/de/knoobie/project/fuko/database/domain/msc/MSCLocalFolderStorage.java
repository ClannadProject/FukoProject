package de.knoobie.project.fuko.database.domain.msc;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public @Getter @Setter abstract class MSCLocalFolderStorage extends MSCEntity implements Serializable {

  @Basic
  @Column(nullable = true)
  private boolean localeStorage;
  @Basic
  @Column(nullable = true)
  private String folderName;
  @Basic
  @Column(nullable = true)
  private String folderLocation;

  public MSCLocalFolderStorage() {

  } 
  
}
