package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.fuko.database.domain.msc.MSCClannadMeta;
import de.knoobie.project.nagisa.gson.model.bo.enums.VGMdbArtistType;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
public @Getter @Setter class Artist extends MSCClannadMeta implements Serializable {
  
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
  
  @OneToOne(optional = true, targetEntity = ArtistBand.class)
  private ArtistBand band;

  @OneToOne(optional = true, targetEntity = ArtistPerson.class)
  private ArtistPerson person;
  
  @ManyToOne(optional = true, targetEntity = ArtistRelease.class)
  private List<ArtistRelease> discography;
  
  @ManyToOne(optional = true, targetEntity = ArtistRelease.class)
  private List<ArtistRelease> featuredOn;
  
  @ManyToMany(targetEntity = Website.class)
  private List<Website> websites;
  
  
  public Artist(){
    
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
