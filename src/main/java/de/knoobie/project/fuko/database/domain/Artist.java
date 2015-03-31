package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.fuko.database.domain.embeddable.AlbumLink;
import de.knoobie.project.fuko.database.domain.embeddable.ArtistLink;
import de.knoobie.project.fuko.database.domain.embeddable.WebsiteLink;
import de.knoobie.project.fuko.database.domain.msc.MSCClannadMeta;
import de.knoobie.project.fuko.database.utils.VGMdbArtistModifier;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbArtist;
import de.knoobie.project.nagisa.gson.model.bo.enums.VGMdbArtistType;
import de.knoobie.project.nagisa.gson.model.bo.enums.VGMdbGender;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ARTIST")
public @Getter
@Setter
class Artist extends MSCClannadMeta implements Serializable {

    @Basic
    @Column(nullable = true)
    // List<String> creditedWorks -> String with ', ' ( Allgemeine Artist Infos: wie viele Alben in DB etc )
    private String creditedWorks;

    // getNames means getAliases
    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private VGMdbArtistType artistType;

    @Embedded
    private Picture picture;

    @Basic
    @Column(nullable = true)
    private String birthdate;
    @Basic
    @Column(nullable = true)
    private String birthplace;
    @Basic
    @Column(nullable = true)
    private String bloodtype;
    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private VGMdbGender gender;

    // info -> Units -> BandMemberOf
    @ElementCollection(targetClass = ArtistLink.class)
    @CollectionTable(name = "artist_bandMemberOf")
    private List<ArtistLink> bandMemberOf = new ArrayList<>();

    @ElementCollection(targetClass = ArtistLink.class)
    @CollectionTable(name = "artist_member")
    private List<ArtistLink> currentMember = new ArrayList<>();

    @ElementCollection(targetClass = ArtistLink.class)
    @CollectionTable(name = "artist_formerMember")
    private List<ArtistLink> formerMember = new ArrayList<>();

    @ElementCollection(targetClass = AlbumLink.class)
    @CollectionTable(name = "artist_discography")
    private List<AlbumLink> discography = new ArrayList<>();

    @ElementCollection(targetClass = AlbumLink.class)
    @CollectionTable(name = "artist_featured_on")
    private List<AlbumLink> featuredOn = new ArrayList<>();

    @Basic
    @Column(nullable = true)
    private String formed;
    
    @ElementCollection(targetClass = WebsiteLink.class)
    @CollectionTable(name = "artist_websites")
    private List<WebsiteLink> websites = new ArrayList<>();

    public Artist() {

    }

    public static Artist getFromVGMDB(VGMdbArtist artist) {
        return VGMdbArtistModifier.transformVGMdbArtist(artist, false);
    }

}
