package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.fuko.database.domain.embeddable.Picture;
import de.knoobie.project.fuko.database.domain.embeddable.AlbumLink;
import de.knoobie.project.fuko.database.domain.embeddable.ArtistLink;
import de.knoobie.project.fuko.database.domain.embeddable.EventLink;
import de.knoobie.project.fuko.database.domain.embeddable.OrganizationLink;
import de.knoobie.project.fuko.database.domain.embeddable.ProductLink;
import de.knoobie.project.fuko.database.domain.embeddable.WebsiteLink;
import de.knoobie.project.fuko.database.domain.msc.MSCClannadMeta;
import de.knoobie.project.fuko.database.utils.VGMdbAlbumModifier;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbAlbum;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
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
public @Getter
@Setter
class Album extends MSCClannadMeta implements Serializable {

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
    private String albumCategorie;
    @Basic
    @Column(nullable = true)
    private Integer albumRating;
    @Basic
    @Column(nullable = true)
    private String releaseDate;
    @Basic
    @Column(nullable = true)
    private String releasePrice;
    @Basic
    @Column(nullable = true)
    private Boolean reprint;
    
    @ElementCollection(targetClass = Picture.class)
    @CollectionTable(name = "album_pictures")
    private List<Picture> pictures = new ArrayList<>();

    @ElementCollection(targetClass = ArtistLink.class)
    @CollectionTable(name = "album_performers")
    private List<ArtistLink> performers = new ArrayList<>();

    @ElementCollection(targetClass = ArtistLink.class)
    @CollectionTable(name = "album_arrangers")
    private List<ArtistLink> arrangers = new ArrayList<>();

    @ElementCollection(targetClass = ArtistLink.class)
    @CollectionTable(name = "album_composers")
    private List<ArtistLink> composers = new ArrayList<>();

    @ElementCollection(targetClass = ArtistLink.class)
    @CollectionTable(name = "album_lyricists")
    private List<ArtistLink> lyricists = new ArrayList<>();

    @OneToMany(targetEntity = AlbumDisc.class, mappedBy = "album", orphanRemoval = true)
    @JoinTable(name = "album_cds")
    private List<AlbumDisc> discs = new ArrayList<>();
    
    @ElementCollection(targetClass = ProductLink.class)
    @CollectionTable(name = "album_represented_products")
    private List<ProductLink> representedProducts = new ArrayList<>();

    @ElementCollection(targetClass = OrganizationLink.class)
    @CollectionTable(name = "album_publishers")
    private List<OrganizationLink> publisher = new ArrayList<>();
    
    @ElementCollection(targetClass = OrganizationLink.class)
    @CollectionTable(name = "album_distributors")
    private List<OrganizationLink> distributors = new ArrayList<>();

    @ElementCollection(targetClass = AlbumLink.class)
    @CollectionTable(name = "album_reprints")
    private List<AlbumLink> reprints = new ArrayList<>();
    
    @ElementCollection(targetClass = AlbumLink.class)
    @CollectionTable(name = "album_related_albums")
    private List<AlbumLink> relatedAlbums = new ArrayList<>();

    @ElementCollection(targetClass = EventLink.class)
    @CollectionTable(name = "album_release_events")
    private List<EventLink> releaseEvents = new ArrayList<>();

    public Album() {

    }

    public static Album getFromVGMDB(VGMdbAlbum album) {
        return VGMdbAlbumModifier.transformVGMdbAlbum(album);
    }

}
