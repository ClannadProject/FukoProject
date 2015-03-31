package de.knoobie.project.fuko.database.domain.embeddable;

import de.knoobie.project.nagisa.gson.model.bo.enums.VGMdbWebsiteType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Embeddable
public @Getter
@Setter
class WebsiteLink extends Link {

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private VGMdbWebsiteType websiteType;

    public WebsiteLink() {
    }

}
