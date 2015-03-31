package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.fuko.database.domain.embeddable.AlbumLink;
import de.knoobie.project.fuko.database.domain.msc.MSCClannadMeta;
import de.knoobie.project.fuko.database.utils.VGMdbEventModifier;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbEvent;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
public @Getter
@Setter
class Event extends MSCClannadMeta implements Serializable {

    @Basic
    @Column(nullable = true)
    private String startDate;
    @Basic
    @Column(nullable = true)
    private String endDate;
    @Basic
    @Column(nullable = true)
    private String shortName;

    @ElementCollection(targetClass = AlbumLink.class)
    @CollectionTable(name = "event_album_releases")
    private List<AlbumLink> releases = new ArrayList<>();

    public Event() {

    }

    public static Event getFromVGMDB(VGMdbEvent event) {
        return VGMdbEventModifier.transformVGMdbEvent(event, false);
    }
}
