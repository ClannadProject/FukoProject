package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.fuko.database.domain.msc.MSCClannadMeta;
import de.knoobie.project.fuko.database.utils.VGMdbOrganizationModifier;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbOrganisation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
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

    @OneToOne(optional = true, targetEntity = Picture.class)
    private Picture picture;

    // getNames --> getAliases!
    @OneToMany(targetEntity = OrganizationRelease.class, mappedBy = "publisher")
    private List<OrganizationRelease> releases = new ArrayList<>();

    public Organization() {

    }  

    public static Organization getFromVGMDB(VGMdbOrganisation org) {
        return VGMdbOrganizationModifier.transformVGMdbOrganisation(org, false);
    }

}
