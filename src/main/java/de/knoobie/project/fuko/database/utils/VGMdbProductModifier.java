package de.knoobie.project.fuko.database.utils;

import de.knoobie.project.clannadutils.common.ListUtils;
import de.knoobie.project.clannadutils.common.StringUtils;
import de.knoobie.project.fuko.database.bo.enums.DataType;
import de.knoobie.project.fuko.database.domain.Product;
import de.knoobie.project.fuko.database.domain.embeddable.ProductLink;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbName;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbProduct;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbProductMerchandise;
import de.knoobie.project.nagisa.gson.model.bo.enums.VGMdbNameLanguage;
import java.util.ArrayList;
import java.util.List;

public class VGMdbProductModifier {
    
    public static List<ProductLink> getMerchandiseLinks(List<VGMdbProductMerchandise> source) {
        List<ProductLink> links = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return links;
        }

        source.stream().forEach((merchandise) -> {
            links.add(getMerchandiseLink(merchandise));
        });

        return links;
    }

    public static ProductLink getMerchandiseLink(VGMdbProductMerchandise source) {
        if (source == null) {
            return null;
        }

        List<VGMdbName> names = new ArrayList<>();
        if (!ListUtils.isEmpty(source.getNames())) {
            names.addAll(source.getNames());
        }
        ProductLink link = new ProductLink();
        link.setPlatform(StringUtils.trim(source.getPlatform()));
        link.setRegion(StringUtils.trim(source.getRegion()));
        link.setReleaseDate(StringUtils.trim(source.getDate()));
        VGMdbCommonModifier.updateLink(link, names, source.getLink());
        return link;
    }
    
    public static List<ProductLink> getLinks(List<VGMdbProduct> source) {
        List<ProductLink> links = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return links;
        }

        source.stream().forEach((product) -> {
            links.add(getLink(product));
        });

        return links;
    }

    public static ProductLink getLink(VGMdbProduct source) {
        if (source == null) {
            return null;
        }

        List<VGMdbName> names = new ArrayList<>();
        if (!StringUtils.isEmpty(source.getName())) {
            names.add(new VGMdbName(StringUtils.trim(source.getName()), VGMdbNameLanguage.eng));
        }
        if (!ListUtils.isEmpty(source.getNames())) {
            names.addAll(source.getNames());
        }
        ProductLink link = new ProductLink();
        VGMdbCommonModifier.updateLink(link, names, source.getLink());
        return link;
    }

    public static List<Product> transformProductList(List<VGMdbProduct> source) {
        List<Product> organisations = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return organisations;
        }

        source.stream().forEach((sourceProduct) -> {
            if (!StringUtils.isEmpty(sourceProduct.getLink())) {
                organisations.add(transformVGMdbProduct(sourceProduct, true));
            }
        });

        return organisations;
    }

    public static Product transformVGMdbProduct(VGMdbProduct source, boolean incompleteSource) {
        if (source == null) {
            return null;
        }

        Product product = new Product();
        product.setName(StringUtils.trim(source.getName()));
        product.setNames(VGMdbCommonModifier.getModifiedNames(source.getNames()));
        VGMdbCommonModifier.addVGMdbID(product, source.getLink(), DataType.PRODUCT);

        if (incompleteSource) {
            return product;
        }

        product.setDescription(StringUtils.trim(source.getDescription()));
        product.setFranchises(getMerchandiseLinks(source.getFranchises()));
        product.setReleases(getMerchandiseLinks(source.getReleases()));
        product.setTitles(getMerchandiseLinks(source.getTitles()));
        product.setProductType(source.getType());
        product.setOrganizations(StringUtils.trim(source.getOrganizations()));
        product.setWebsites(VGMdbCommonModifier.getWebsiteLinks(source.getWebsites()));
        product.setRelatedAlbums(VGMdbAlbumModifier.getAlbumLinks(source.getAlbums()));
        product.setReleaseDate(StringUtils.trim(source.getReleaseDate()));
        product.setVgmdbLink(StringUtils.trim(source.getVgmdbLink()));
        product.setPicture(VGMdbCommonModifier.getModifiedPicture(source.getPicture(), true));

        return product;
    }
}
