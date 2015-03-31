package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.nagisa.gson.model.bo.enums.VGMdbNameLanguage;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
public @Getter
@Setter
class Name implements Serializable {

  @Basic
  @Column(nullable = true)
  private String name;
  @Basic
  @Column(nullable = true)
  private VGMdbNameLanguage nameLanguage;

  public Name() {

  }

}
