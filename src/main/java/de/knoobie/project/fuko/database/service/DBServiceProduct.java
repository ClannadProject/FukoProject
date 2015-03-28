package de.knoobie.project.fuko.database.service;

import de.knoobie.project.fuko.database.domain.Product;
import de.knoobie.project.fuko.database.domain.Search;
import java.io.Serializable;

public class DBServiceProduct extends AbstractDBService<Product> implements Serializable {

    DBServiceProduct(final FukoDB database) {
        super(database);
    }

    @Override
    public Product findBy(int vgmdbID) {
      return findBy(Product.class, vgmdbID);
    }

    @Override
    protected Product updateDatabaseRelations(Product product) {
        product.getFranchises().replaceAll(this::getORadd);
        product.getTitles().replaceAll(this::getORadd);
        product.getRelatedAlbums().replaceAll(database.getAlbumService()::getORadd);
        return product;
    }
    
    @Override
    public Search updateSearch(Search arg) {
        arg.getProducts().replaceAll(this::getORadd);
        return arg;
    }
}
