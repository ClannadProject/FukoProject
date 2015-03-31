package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.fuko.database.domain.embeddable.AlbumLink;
import de.knoobie.project.fuko.database.domain.msc.MSCClannadMeta;
import de.knoobie.project.fuko.database.utils.VGMdbOrganizationModifier;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbOrganisation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
public @Getter
@Setter
class Organization extends MSCClannadMeta implements Serializable {

    @Basic
    @Column(nullable = true)
    private String region;
    @Basic
    @Column(nullable = true)
    private String organizationType;

    @Embedded
    private Picture picture;

    // getNames --> getAliases!
    
    @ElementCollection(targetClass = AlbumLink.class)        
    @CollectionTable(name = "organization_album_releases")
    private List<AlbumLink> releases = new ArrayList<>();
    
//    @OneToMany(targetEntity = OrganizationRelease.class, mappedBy = "publisher")
//    private List<OrganizationRelease> releases = new ArrayList<>();

    public Organization() {

    }  

    public static Organization getFromVGMDB(VGMdbOrganisation org) {
        return VGMdbOrganizationModifier.transformVGMdbOrganisation(org, false);
    }

}
