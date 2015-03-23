package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.fuko.database.domain.msc.MSCEntity;
import de.knoobie.project.fuko.database.domain.msc.MSCVGMdbEntity;
import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
public @Getter @Setter class ProductMerchandise extends MSCVGMdbEntity implements Serializable{
  
  
  @Basic
  @Column(nullable = true)
  private Date releaseDate;
  @Basic
  @Column(nullable = true)
  private String platform;
  @Basic
  @Column(nullable = true)
  private String region;
  
  
  
  public ProductMerchandise(){
    
  }
  
}
