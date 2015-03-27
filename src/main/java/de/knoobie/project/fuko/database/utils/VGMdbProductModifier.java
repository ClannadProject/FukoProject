package de.knoobie.project.fuko.database.utils;

import de.knoobie.project.clannadutils.common.DateUtils;
import de.knoobie.project.clannadutils.common.ListUtils;
import de.knoobie.project.clannadutils.common.StringUtils;
import de.knoobie.project.fuko.database.bo.enums.DataType;
import de.knoobie.project.fuko.database.domain.Product;
import de.knoobie.project.fuko.database.domain.ProductRelease;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbProduct;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbProductMerchandise;
import de.knoobie.project.nagisa.gson.model.bo.enums.VGMdbProductType;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class VGMdbProductModifier {

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

        product.setClannadAdded(new Date(DateUtils.getNow().getTime()));
        product.setClannadUpdated(product.getClannadAdded());
        product.setDescription(StringUtils.trim(source.getDescription()));
        product.setFranchises(getProductsFromLists(source.getFranchises(), null));
        product.setReleases(getProductReleasesFromLists(source.getReleases()));
        product.setTitles(getProductsFromLists(source.getTitles(), null));
        product.setProductType(source.getType());
        product.setOrganizations(StringUtils.trim(source.getOrganizations()));
        product.setWebsites(VGMdbCommonModifier.getModifiedWebsites(source.getWebsites()));
        product.setRelatedAlbums(VGMdbAlbumModifier.transformAlbumList(source.getAlbums()));
        product.setReleaseDate(StringUtils.trim(source.getReleaseDate()));
        product.setVgmdbLink(StringUtils.trim(source.getVgmdbLink()));
        product.setPicture(VGMdbCommonModifier.getModifiedPicture(source.getPicture()));
        
        return product;
    }

    private static List<ProductRelease> getProductReleasesFromLists(List<VGMdbProductMerchandise> source) {
        List<ProductRelease> products = new ArrayList<>();
        if (ListUtils.isEmpty(source)) {
            return products;
        }

        source.stream().forEach((sourceProduct) -> {
            if (!StringUtils.isEmpty(sourceProduct.getLink())) {
                ProductRelease product = new ProductRelease();
                product.setNames(VGMdbCommonModifier.getModifiedNames(sourceProduct.getNames()));
                product.setReleaseDate(StringUtils.trim(sourceProduct.getDate()));
                product.setPlatform(StringUtils.trim(sourceProduct.getPlatform()));
                product.setRegion(StringUtils.trim(sourceProduct.getRegion()));
                VGMdbCommonModifier.addVGMdbID(product, sourceProduct.getLink(), DataType.RELEASE);

                products.add(product);
            }
        });

        return products;
    }    
    
    private static List<Product> getProductsFromLists(List<VGMdbProductMerchandise> source, VGMdbProductType productType) {
        List<Product> products = new ArrayList<>();
        if (ListUtils.isEmpty(source)) {
            return products;
        }

        source.stream().forEach((sourceProduct) -> {
            if (!StringUtils.isEmpty(sourceProduct.getLink())) {
                Product product = new Product();
                product.setNames(VGMdbCommonModifier.getModifiedNames(sourceProduct.getNames()));
                product.setReleaseDate(StringUtils.trim(sourceProduct.getDate()));
                VGMdbCommonModifier.addVGMdbID(product, sourceProduct.getLink(), DataType.PRODUCT);
                if(productType != null){
                    product.setProductType(productType);
                }
                products.add(product);
            }
        });

        return products;
    }
}
