package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.fuko.database.domain.msc.MSCEntity;
import de.knoobie.project.nagisa.gson.model.bo.enums.VGMdbNameLanguage;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
public @Getter
@Setter
class Name extends MSCEntity implements Serializable {

  @Basic
  @Column(nullable = true)
  private String name;
  @Basic
  @Column(nullable = true)
  private VGMdbNameLanguage nameLanguage;

  public Name() {

  }

}
