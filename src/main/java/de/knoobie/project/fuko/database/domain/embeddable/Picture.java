package de.knoobie.project.fuko.database.domain.embeddable;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
public @Getter @Setter class Picture implements Serializable {

  @Basic
  @Column(nullable = true)
  private boolean cover;
  @Basic
  @Column(nullable = true)
  private String urlSmall;
  @Basic
  @Column(nullable = true)
  private String urlThumbnail;
  @Basic
  @Column(nullable = true)
  private String urlFull;  
    @Basic
  @Column(nullable = true)
  private boolean pictureLocaleStorage;
  @Basic
  @Column(nullable = true)
  private String pictureName;
  @Basic
  @Column(nullable = true)
  private String pictureLocation;
  @Basic
  @Column(nullable = true)
  private String pictureExtension;

  public Picture() {

  }
}
