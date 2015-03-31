package de.knoobie.project.fuko.database.domain.embeddable;

import de.knoobie.project.fuko.database.bo.enums.DataType;
import de.knoobie.project.fuko.database.domain.Name;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    @Column(nullable = true)    
    @Enumerated(EnumType.STRING)
    private DataType type;
    
    @Column(nullable = true)
    private Name primaryName;    

    public Link() {

    }

}
