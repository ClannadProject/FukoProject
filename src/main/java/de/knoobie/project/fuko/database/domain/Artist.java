package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.fuko.database.domain.msc.MSCClannadMeta;
import de.knoobie.project.nagisa.gson.model.bo.enums.VGMdbArtistType;
import de.knoobie.project.nagisa.gson.model.bo.enums.VGMdbGender;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
    private String variations;
    @Basic
    @Column(nullable = true)
    // List<String> creditedWorks -> String with ', ' ( Allgemeine Artist Infos: wie viele Alben in DB etc )
    private String creditedWorks;

    // getNames means getAliases
    @Basic
    @Column(nullable = true)
    private VGMdbArtistType artistType;

    @OneToOne(optional = true, targetEntity = VGMdbPicture.class)
    private VGMdbPicture picture;

    @Basic
    @Column(nullable = true)
    private String birthdate;
    @Basic
    @Column(nullable = true)
    private String birthplace;
    @Basic
    @Column(nullable = true)
    private String bloodtype;
    @Basic
    @Column(nullable = true)
    private VGMdbGender gender;

    // info -> Units -> BandMemberOf
    @JoinTable(name = "artist_bandMemberOf")
    @ManyToMany(targetEntity = Artist.class)
    private List<Artist> bandMemberOf = new ArrayList<>();

    @Basic
    @Column(nullable = true)
    private String formed;

    @JoinTable(name = "artist_Member")
    @ManyToMany(targetEntity = Artist.class)
    private List<Artist> member = new ArrayList<>();

    @JoinTable(name = "artist_formerMember")
    @ManyToMany(targetEntity = Artist.class)
    private List<Artist> formerMember = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "artist")
    private List<ArtistRelease> discography = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "artist")
    private List<ArtistRelease> featuredOn = new ArrayList<>();

    @JoinColumn(nullable = true)
    @ManyToMany(targetEntity = Website.class, cascade = CascadeType.ALL)
    private List<Website> websites = new ArrayList<>();

    public Artist() {

    }

    public String getVariations() {
        return variations;
    }

    public void setVariations(String variations) {
        this.variations = variations;
    }

    public String getCreditedWorks() {
        return creditedWorks;
    }

    public void setCreditedWorks(String creditedWorks) {
        this.creditedWorks = creditedWorks;
    }

    public VGMdbArtistType getArtistType() {
        return artistType;
    }

    public void setArtistType(VGMdbArtistType artistType) {
        this.artistType = artistType;
    }

    public VGMdbPicture getPicture() {
        return picture;
    }

    public void setPicture(VGMdbPicture picture) {
        this.picture = picture;
    }

    public List<ArtistRelease> getDiscography() {
        return discography;
    }

    public void setDiscography(List<ArtistRelease> discography) {
        this.discography = discography;
    }

    public List<ArtistRelease> getFeaturedOn() {
        return featuredOn;
    }

    public void setFeaturedOn(List<ArtistRelease> featuredOn) {
        this.featuredOn = featuredOn;
    }

    public List<Website> getWebsites() {
        return websites;
    }

    public void setWebsites(List<Website> websites) {
        this.websites = websites;
    }

}
