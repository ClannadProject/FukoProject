package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.fuko.database.domain.embeddable.Picture;
import de.knoobie.project.fuko.database.domain.msc.MSCEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
public @Getter
@Setter
class Playlist extends MSCEntity {

    @Basic
    @Column(nullable = false)
    private String name;
    @Basic
    @Column(nullable = true)
    private String description;
    @Basic
    @Column(nullable = true)
    private boolean privateList;

    @Embedded
    private Picture cover;

    @JoinTable(name = "playlist_tracklist")
    @ManyToMany(targetEntity = AlbumTrack.class)
    private List<AlbumTrack> tracks = new ArrayList<>();

}
