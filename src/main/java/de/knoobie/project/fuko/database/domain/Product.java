package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.fuko.database.domain.msc.MSCClannadMeta;
import de.knoobie.project.fuko.database.utils.VGMdbProductModifier;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbProduct;
import de.knoobie.project.nagisa.gson.model.bo.enums.VGMdbProductType;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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

    @Basic
    @Column(nullable = true)
    private VGMdbProductType productType;

    @OneToOne(optional = true, targetEntity = Picture.class)
    private Picture picture;

    @ManyToMany(targetEntity = Product.class)
    @JoinTable(name = "product_titles")
    private List<Product> titles = new ArrayList<>();

    @ManyToMany(targetEntity = Product.class)
    @JoinTable(name = "product_franchises")
    private List<Product> franchises = new ArrayList<>();

    @ManyToMany(targetEntity = Website.class)
    @JoinTable(name = "product_websites")
    private List<Website> websites = new ArrayList<>();

    @OneToMany(targetEntity = ProductRelease.class, mappedBy = "product")
    @JoinTable(name = "product_releases")
    private List<ProductRelease> releases = new ArrayList<>();

    @ManyToMany(targetEntity = Album.class)
    @JoinTable(name = "product_related_abums")
    private List<Album> relatedAlbums = new ArrayList<>();

    public Product() {

    }

    public static Product getFromVGMDB(VGMdbProduct product) {
        return VGMdbProductModifier.transformVGMdbProduct(product, false);
    }
}
