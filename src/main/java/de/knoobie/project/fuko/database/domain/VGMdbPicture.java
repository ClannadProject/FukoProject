package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.fuko.database.domain.msc.MSCLocalFileStorage;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
public @Getter @Setter class VGMdbPicture extends MSCLocalFileStorage implements Serializable {

  @Basic
  @Column(nullable = true)
  private String name;
  @Basic
  @Column(nullable = true)
  private String urlSmall;
  @Basic
  @Column(nullable = true)
  private String urlThumbnail;
  @Basic
  @Column(nullable = true)
  private String urlFull;

  public VGMdbPicture() {

  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrlSmall() {
    return urlSmall;
  }

  public void setUrlSmall(String urlSmall) {
    this.urlSmall = urlSmall;
  }

  public String getUrlThumbnail() {
    return urlThumbnail;
  }

  public void setUrlThumbnail(String urlThumbnail) {
    this.urlThumbnail = urlThumbnail;
  }

  public String getUrlFull() {
    return urlFull;
  }

  public void setUrlFull(String urlFull) {
    this.urlFull = urlFull;
  }

}
