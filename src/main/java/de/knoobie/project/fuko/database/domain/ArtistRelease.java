package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.fuko.database.domain.msc.MSCEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "ARTIST_RELEASE")
public @Getter
@Setter
class ArtistRelease extends MSCEntity implements Serializable {

    @Basic
    @Column(nullable = true)
    // parse List<String> roles to roles ', '
    private String roles;

    @ManyToOne(targetEntity = Artist.class)
    private Artist artist;

    @ManyToOne(targetEntity = Album.class)
    private Album album;

    public ArtistRelease() {

    }

}
