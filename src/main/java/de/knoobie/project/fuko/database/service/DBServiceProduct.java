package de.knoobie.project.fuko.database.service;

import de.knoobie.project.clannadutils.bo.DBResult;
import de.knoobie.project.clannadutils.interfaces.DBService;
import de.knoobie.project.fuko.database.domain.Artist;
import de.knoobie.project.fuko.database.domain.Product;
import java.io.Serializable;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class DBServiceProduct implements DBService<Product>, Serializable {

    private final FukoDB database;

    DBServiceProduct(final FukoDB database) {
        if (database == null) {
            throw new IllegalArgumentException("Übergabeparameter dürfen nicht null sein (database = "
                    + database + ")!");
        }

        this.database = database;
    }

    @Override
    public DBResult add(Product product) {
        product.setId(getProductIDbyVGMDBID(product.getVgmdbID()));
        updateProductAndReferences(product);
        return database.update(product);
    }

    private Product updateProductAndReferences(Product product) {
//        album.getArrangers().replaceAll(database.getArtistService()::getORadd);
//        album.getComposers().replaceAll(database.getArtistService()::getORadd);
//        album.getLyricists().replaceAll(database.getArtistService()::getORadd);
//        album.getPerformers().replaceAll(database.getArtistService()::getORadd);
//        album.getRelatedAlbums().replaceAll(this::getORadd);
//        album.getReprints().replaceAll(this::getORadd);
        // Tracks & Cds dürften so gehen?
        // @todo related discs & related products

        return product;
    }

    @Override
    public DBResult update(Product args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DBResult remove(Product args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Product findBy(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Product findBy(int vgmdbID) {
        try {
            CriteriaBuilder criteriaBuilder = database.getEntityManager().getCriteriaBuilder();
            CriteriaQuery cq = criteriaBuilder.createQuery();
            Root<Artist> e = cq.from(Product.class);
            cq.where(criteriaBuilder.equal(e.get("vgmdbID"), criteriaBuilder.parameter(Integer.class, "vgmdbID")));
            Query query = database.getEntityManager().createQuery(cq);
            query.setParameter("vgmdbID", vgmdbID);
            return (Product) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Product getORadd(Product arg) {
        Product realProduct = findBy(arg.getVgmdbID());
        if (realProduct == null) {
            database.update(arg);
            realProduct = findBy(arg.getVgmdbID());
        } 
        return realProduct;
    }

    public Long getProductIDbyVGMDBID(int vgmdbID) {
        try {
            CriteriaBuilder criteriaBuilder = database.getEntityManager().getCriteriaBuilder();
            CriteriaQuery cq = criteriaBuilder.createQuery();
            Root<Artist> e = cq.from(Product.class);
            cq.where(criteriaBuilder.equal(e.get("vgmdbID"), criteriaBuilder.parameter(Integer.class, "vgmdbID")));
            Query query = database.getEntityManager().createQuery(cq);
            query.setParameter("vgmdbID", vgmdbID);
            return ((Product) query.getSingleResult()).getId();
        } catch (NoResultException e) {
            return null;
        }
    }

}
