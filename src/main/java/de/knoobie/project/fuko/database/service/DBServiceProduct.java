package de.knoobie.project.fuko.database.service;

import de.knoobie.project.fuko.database.domain.Product;

public class DBServiceProduct extends AbstractDBService<Product>{

    DBServiceProduct(final FukoDB database) {
        super(database);
    }

    @Override
    public Product findBy(int vgmdbID) {
      return findBy(Product.class, vgmdbID);
    }

    @Override
    protected Product updateDatabaseRelations(Product product) {
        return product;
    }
}
