package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.fuko.database.domain.msc.MSCClannadMeta;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
public @Getter @Setter class Album extends MSCClannadMeta implements Serializable {

  @Basic
  @Column(nullable = true)
  private String albumCatalog;
  @Basic
  @Column(nullable = true)
  // merged to String with 'value, value'
  private String classification;
  @Basic
  @Column(nullable = true)
  // List<String> platforms -> merged to String with 'value, value'
  private String platforms;
  @Basic
  @Column(nullable = true)
  private String mediaFormat;
  @Basic
  @Column(nullable = true)
  private String releaseFormat;
  @Basic
  @Column(nullable = true)
  private String releaseCurrency;
  @Basic
  @Column(nullable = true)
  private String albumType;
  @Basic
  @Column(nullable = true)
  private Date releaseDate;
  @Basic
  @Column(nullable = true)
  private Double releasePrice;
  @Basic
  @Column(nullable = true)
  private Boolean reprint;
  @Basic
  @Column(nullable = true)
  private Boolean localStorage;

  @OneToOne(optional = true, targetEntity = VGMdbPicture.class)
  private VGMdbPicture cover;

  @ManyToMany(targetEntity = VGMdbPicture.class)
  private List<VGMdbPicture> pictures;

  @ManyToMany(targetEntity = Artist.class)
  @JoinTable(name = "album_performers")
  private List<Artist> performers;

  @ManyToMany(targetEntity = Artist.class)
  @JoinTable(name = "album_arrangers")
  private List<Artist> arrangers;
  
  @ManyToMany(targetEntity = Artist.class)
  @JoinTable(name = "album_composers")
  private List<Artist> composers;
  
  @ManyToMany(targetEntity = Artist.class)
  @JoinTable(name = "album_lyricists")
  private List<Artist> lyricists;
  
  @OneToMany(targetEntity = AlbumDisc.class, mappedBy = "album")
  @JoinTable(name = "album_cds")
  private List<AlbumDisc> discs;
  

  @ManyToMany(targetEntity = Product.class)
  @JoinTable(name = "album_represented_products")
  private List<Product> representedProducts;

  @ManyToOne(optional = true, targetEntity = Organisation.class)
  private Organisation publisher;

  @ManyToOne(optional = true, targetEntity = Organisation.class)
  private Organisation distributor;
  
  @ManyToMany(targetEntity = Album.class)
  @JoinTable(name = "album_reprints")
  private List<Album> reprints;

  @ManyToMany(targetEntity = Album.class)
  @JoinTable(name = "album_related_albums")
  private List<Album> relatedAlbums;

  @ManyToMany(targetEntity = Store.class)
  private List<Store> stores;

  @ManyToMany(targetEntity = Website.class)
  private List<Website> websites;


  public Album() {
    

  }

}
