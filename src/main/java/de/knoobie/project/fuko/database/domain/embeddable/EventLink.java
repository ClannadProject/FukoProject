package de.knoobie.project.fuko.database.domain.embeddable;

import de.knoobie.project.fuko.database.bo.enums.DataType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
public @Getter
@Setter
class EventLink extends Link {

    @Basic
    @Column(nullable = true)
    private DataType type = DataType.EVENT;

    public EventLink() {
    }
    
    
}
