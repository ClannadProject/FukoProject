package de.knoobie.project.fuko.database.domain.msc;

import de.knoobie.project.fuko.database.domain.Name;
import de.knoobie.project.fuko.database.bo.enums.DataType;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public @Getter
@Setter
abstract class MSCVGMdbEntity extends MSCEntity implements Serializable {

  @Basic
  private String name;
  @Basic
  private String link;
//  @Id
  @Basic
  @Column(nullable = true)
  private Integer vgmdbID;
  @Basic
  @Column(nullable = true)
  private DataType type;
//  @Id
  @Basic
  @Column(nullable = true, columnDefinition="TEXT")
  private String description;

  @ElementCollection
  private List<Name> names;

  public MSCVGMdbEntity() {

  }

}
