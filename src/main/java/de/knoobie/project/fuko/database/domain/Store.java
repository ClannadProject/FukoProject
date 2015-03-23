package de.knoobie.project.fuko.database.domain;


import de.knoobie.project.fuko.database.domain.msc.MSCVGMdbEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
public @Getter @Setter class Store extends MSCVGMdbEntity implements Serializable {


    public Store() {

    }
}
