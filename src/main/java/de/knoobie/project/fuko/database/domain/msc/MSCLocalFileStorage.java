package de.knoobie.project.fuko.database.domain.msc;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public @Getter @Setter abstract class MSCLocalFileStorage extends MSCEntity implements Serializable {

  @Basic
  @Column(nullable = true)
  private boolean localeStorage;
  @Basic
  @Column(nullable = true)
  private String fileName;
  @Basic
  @Column(nullable = true)
  private String fileLocation;
  @Basic
  @Column(nullable = true)
  private String fileExtension;

  public MSCLocalFileStorage() {

  }  
  
}
