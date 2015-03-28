package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.fuko.database.domain.msc.MSCEntity;
import de.knoobie.project.fuko.database.domain.msc.MSCVGMdbEntity;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name="album_releases")
public @Getter
@Setter
class OrganizationRelease extends MSCEntity implements Serializable {

    @ManyToOne(optional = true, targetEntity = Organization.class)
    private Organization publisher;

    @ManyToOne(optional = true, targetEntity = Album.class)
    private Album album;

    @OneToOne(optional = true, targetEntity = Event.class)
    private Event releaseEvent;
    
    @Basic
    @Column(nullable = true)
    private String organizationRole;


    public OrganizationRelease() {

    }

}
