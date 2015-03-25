package de.knoobie.project.fuko.database.utils;

import de.knoobie.project.clannadutils.common.ListUtils;
import de.knoobie.project.clannadutils.common.StringUtils;
import de.knoobie.project.fuko.database.bo.enums.DataType;
import de.knoobie.project.fuko.database.domain.Product;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbProduct;
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
        
        if(incompleteSource){
            return product;
        }

        return product;
    }
}
