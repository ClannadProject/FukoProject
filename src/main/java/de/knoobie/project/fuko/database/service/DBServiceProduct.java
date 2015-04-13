package de.knoobie.project.fuko.database.service;

import de.knoobie.project.fuko.database.domain.Product;

public class DBServiceProduct extends AbstractVGMdbService<Product>{

    DBServiceProduct(final FukoDB database) {
        super(database);
    }

    @Override
    public Product findByVGMdbID(int vgmdbID) {
      return findBy(Product.class, vgmdbID);
    }

    @Override
    protected Product updateDatabaseRelations(Product product) {
        return product;
    }

    public Product updateFileSystem(Product product) {
        Product dbProduct = findByVGMdbID(product.getVgmdbID());

        if (dbProduct == null) {
            return product;
        }
        dbProduct.setPicture(product.getPicture());
        dbProduct.setFolderLocation(product.getFolderLocation());
        dbProduct.setFolderName(product.getFolderName());
        dbProduct.setLocaleStorage(product.isLocaleStorage());

        return (Product) database.update(dbProduct).getResult();
    }
}
