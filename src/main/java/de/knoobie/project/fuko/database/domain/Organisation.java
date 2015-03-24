package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.fuko.database.domain.msc.MSCClannadMeta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
public @Getter @Setter class Organisation extends MSCClannadMeta implements Serializable {

  @Basic
  @Column(nullable = true)
  private String region;
  @Basic
  @Column(nullable = true)
  private String organisationType;
  
  @OneToOne(optional = true, targetEntity = VGMdbPicture.class)
  private VGMdbPicture picture;
  
  // getNames --> getAliases!
  
  @OneToMany(targetEntity = OrganisationRelease.class, mappedBy = "publisher")
  private List<OrganisationRelease> releases = new ArrayList<>();
  
  public Organisation() {

  }  
  
}
