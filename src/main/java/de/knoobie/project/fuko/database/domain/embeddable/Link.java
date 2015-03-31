package de.knoobie.project.fuko.database.domain.embeddable;

import de.knoobie.project.fuko.database.bo.enums.DataType;
import de.knoobie.project.fuko.database.domain.Name;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public abstract @Getter @Setter class Link implements Serializable {

    @Basic
    @Column(nullable = true)
    private String link;

    @Basic
    @Column(nullable = true)
    private Integer vgmdbID;
    @Basic
    @Column(nullable = true)
    private DataType type;
//
//    @ElementCollection
//    private List<Name> names;

    public Link() {

    }

}
