package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.fuko.database.domain.msc.MSCEntity;
import de.knoobie.project.fuko.database.domain.msc.MSCLocalFileStorage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
public @Getter
@Setter
class AlbumTrack extends MSCLocalFileStorage implements Serializable {

    @Basic
    @Column(nullable = true)
    private String trackLength;

    @Basic
    @Column(nullable = true)
    private Integer trackPosition;

    @Basic
    @Column(nullable = true)
    private Integer trackRating;

    @ElementCollection
    private List<Name> trackNames = new ArrayList<>();

    @ManyToOne(optional = true, targetEntity = AlbumDisc.class)
    private AlbumDisc cd;

    public AlbumTrack() {

    }
}
