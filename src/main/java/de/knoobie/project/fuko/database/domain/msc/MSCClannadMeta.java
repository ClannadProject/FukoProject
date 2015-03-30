package de.knoobie.project.fuko.database.domain.msc;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public @Getter @Setter abstract class MSCClannadMeta extends MSCVGMdbMeta implements Serializable {

  @Basic
  @Column(nullable = true)
  private Timestamp clannadAdded;
  @Basic
  @Column(nullable = true)
  private Timestamp clannadUpdated;

  public MSCClannadMeta() {

  }

}
