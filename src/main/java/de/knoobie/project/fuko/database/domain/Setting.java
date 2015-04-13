package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.fuko.database.bo.enums.ClannadSetting;
import de.knoobie.project.fuko.database.domain.msc.MSCEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public @Getter
@Setter
class Setting implements Serializable {

    @Id()
    @Enumerated(EnumType.STRING)
    private ClannadSetting type;

    @Basic
    @Column(nullable = true)
    private String setting;

    public Setting() {

    }
}
