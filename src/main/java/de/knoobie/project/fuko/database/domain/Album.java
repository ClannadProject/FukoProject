package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.fuko.database.domain.msc.MSCClannadMeta;
import de.knoobie.project.fuko.database.utils.VGMdbAlbumModifier;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbAlbum;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ALBUM")
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
  private Integer albumRating;
  @Basic
  @Column(nullable = true)
  private String releaseDate;
  @Basic
  @Column(nullable = true)
  private Double releasePrice;
  @Basic
  @Column(nullable = true)
  private Boolean reprint;
  @Basic
  @Column(nullable = true)
  private Boolean localStorage;

  @OneToOne(optional = true, targetEntity = Picture.class)
  private Picture cover;

  @ManyToMany(targetEntity = Picture.class)
  private List<Picture> pictures = new ArrayList<>();

  @ManyToMany(targetEntity = Artist.class)
  @JoinTable(name = "album_performers")
  private List<Artist> performers = new ArrayList<>();

  @ManyToMany(targetEntity = Artist.class)
  @JoinTable(name = "album_arrangers")
  private List<Artist> arrangers = new ArrayList<>();
  
  @ManyToMany(targetEntity = Artist.class)
  @JoinTable(name = "album_composers")
  private List<Artist> composers = new ArrayList<>();
  
  @ManyToMany(targetEntity = Artist.class)
  @JoinTable(name = "album_lyricists")
  private List<Artist> lyricists = new ArrayList<>();
  
  @OneToMany(targetEntity = AlbumDisc.class, mappedBy = "album")
  @JoinTable(name = "album_cds")
  private List<AlbumDisc> discs = new ArrayList<>();
  

  @ManyToMany(targetEntity = Product.class)
  @JoinTable(name = "album_represented_products")
  private List<Product> representedProducts = new ArrayList<>();

  @ManyToOne(optional = true, targetEntity = Organisation.class)
  private Organisation publisher;

  @ManyToOne(optional = true, targetEntity = Organisation.class)
  private Organisation distributor;
  
  @ManyToMany(targetEntity = Album.class)
  @JoinTable(name = "album_reprints")
  private List<Album> reprints = new ArrayList<>();

  @ManyToMany(targetEntity = Album.class)
  @JoinTable(name = "album_related_albums")
  private List<Album> relatedAlbums = new ArrayList<>();

  @ManyToMany(targetEntity = Store.class)
  private List<Store> stores = new ArrayList<>();

  @ManyToMany(targetEntity = Website.class)
  private List<Website> websites = new ArrayList<>();
  
  @JoinTable(name = "event_album_releases")
  @ManyToMany(targetEntity = Event.class, mappedBy = "releases")
  private List<Event> events = new ArrayList<>();


  public Album() {
    

  }

    public static Album getFromVGMDB(VGMdbAlbum album) {
        return VGMdbAlbumModifier.transformVGMdbAlbum(album, false);
    }

}
