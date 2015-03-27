package de.knoobie.project.fuko.database.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
public @Getter @Setter class OrganisationRelease extends Album implements Serializable {

  @Basic
  @Column(nullable = true)
  private String organisationRole;
  
  @ManyToOne(optional = true, targetEntity = Event.class)
  private Event releaseEvent;

  public OrganisationRelease() {

  }
  
}
