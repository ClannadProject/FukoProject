package de.knoobie.project.fuko.database.domain.embeddable;

import de.knoobie.project.fuko.database.bo.enums.DataType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Embeddable
public @Getter
@Setter
class AlbumLink extends Link {

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private DataType type = DataType.ALBUM;

    @Basic
    @Column(nullable = true)
    private String albumCatalog;

    @Basic
    @Column(nullable = true)
    private String releaseDate;

    @Basic
    @Column(nullable = true)
    private String releaseType;

    @Basic
    @Column(nullable = true)
    private String artistRole;

    @Basic
    @Column(nullable = true)
    private String albumType;

    @Basic
    @Column(nullable = true)
    private OrganizationLink publisher;

    @Basic
    @Column(nullable = true)
    private EventLink releaseEvent;

    @Basic
    @Column(nullable = true)
    private String role;

    public AlbumLink() {
    }

}
