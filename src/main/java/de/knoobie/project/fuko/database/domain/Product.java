package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.fuko.database.domain.embeddable.AlbumLink;
import de.knoobie.project.fuko.database.domain.embeddable.ProductLink;
import de.knoobie.project.fuko.database.domain.embeddable.WebsiteLink;
import de.knoobie.project.fuko.database.domain.msc.MSCClannadMeta;
import de.knoobie.project.fuko.database.utils.VGMdbProductModifier;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbProduct;
import de.knoobie.project.nagisa.gson.model.bo.enums.VGMdbProductType;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
public @Getter
@Setter
class Product extends MSCClannadMeta implements Serializable {

    @Basic
    @Column(nullable = true)
    private String organizations;

    @Basic
    @Column(nullable = true)
    private String releaseDate;

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private VGMdbProductType productType;

    @Embedded
    private Picture picture;

    @ElementCollection(targetClass = ProductLink.class)
    @CollectionTable(name = "product_titles")
    private List<ProductLink> titles = new ArrayList<>();

    @ElementCollection(targetClass = ProductLink.class)
    @CollectionTable(name = "product_franchises")
    private List<ProductLink> franchises = new ArrayList<>();

    @ElementCollection(targetClass = ProductLink.class)
    @CollectionTable(name = "product_releases")
    private List<ProductLink> releases = new ArrayList<>();

    @ElementCollection(targetClass = WebsiteLink.class)
    @CollectionTable(name = "product_websites")
    private List<WebsiteLink> websites = new ArrayList<>();

    @ElementCollection(targetClass = AlbumLink.class)
    @CollectionTable(name = "product_related_abums")
    private List<AlbumLink> relatedAlbums = new ArrayList<>();

    public Product() {

    }

    public static Product getFromVGMDB(VGMdbProduct product) {
        return VGMdbProductModifier.transformVGMdbProduct(product, false);
    }
}
