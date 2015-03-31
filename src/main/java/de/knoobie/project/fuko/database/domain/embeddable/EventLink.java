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
class EventLink extends Link {

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private DataType type = DataType.EVENT;
    
    @Basic
    @Column(nullable = true)
    private String shortname;

    public EventLink() {
    }
    
    
}
