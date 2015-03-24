package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.fuko.database.domain.msc.MSCClannadMeta;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
public @Getter
@Setter
class Event extends MSCClannadMeta implements Serializable {

  @Basic
  @Column(nullable = true)
  private String startDate;
  @Basic
  @Column(nullable = true)
  private String endDate;
  @Basic
  @Column(nullable = true)
  private String shortName;

  @ManyToMany(targetEntity = Album.class)
  private List<Album> releases = new ArrayList<>();

  public Event() {

  }
}
