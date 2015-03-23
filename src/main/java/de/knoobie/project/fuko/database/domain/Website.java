package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.fuko.database.domain.msc.MSCVGMdbEntity;
import de.knoobie.project.nagisa.gson.model.bo.enums.VGMdbWebsiteType;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
public @Getter @Setter class Website extends MSCVGMdbEntity implements Serializable {

  @Basic
  @Column(nullable = true)
  private VGMdbWebsiteType websiteType;

  public Website() {

  }  
  
}
