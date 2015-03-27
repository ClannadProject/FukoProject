package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.fuko.database.domain.msc.MSCVGMdbEntity;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
public @Getter @Setter class ProductRelease extends MSCVGMdbEntity implements Serializable{
  
  
  @Basic
  @Column(nullable = true)
  private String releaseDate;
  
  @OneToMany(targetEntity = Name.class)
  private List<Name> names;
  
  @Basic
  @Column(nullable = true)
  private String region;
  @Basic
  @Column(nullable = true)
  private String platform;
  
  @ManyToOne(optional = true, targetEntity = Product.class)
  private Product product;
  
  
  
  public ProductRelease(){
    
  }
  
}
