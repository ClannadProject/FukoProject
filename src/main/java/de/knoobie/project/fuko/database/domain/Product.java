package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.fuko.database.domain.msc.MSCClannadMeta;
import de.knoobie.project.nagisa.gson.model.bo.enums.VGMdbProductType;
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
public @Getter @Setter class Product extends MSCClannadMeta implements Serializable {

  @Basic
  @Column(nullable = true)
  private String organizations;

  @Basic
  @Column(nullable = true)
  private String releaseDate;
  
  @Basic
  @Column(nullable = true)
  private VGMdbProductType productType;
  
  
  @ManyToMany(targetEntity = ProductMerchandise.class)
  private List<ProductMerchandise> titles = new ArrayList<>();
  
  @ManyToMany(targetEntity = ProductMerchandise.class)
  private List<ProductMerchandise> francises = new ArrayList<>();
  
  @ManyToMany(targetEntity = ProductMerchandise.class)
  private List<ProductMerchandise> releases = new ArrayList<>();
  
  @ManyToMany(targetEntity = Album.class)
  private List<Album> relatedAlbums = new ArrayList<>();

  public Product() {

  }
}
