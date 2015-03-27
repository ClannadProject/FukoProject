package de.knoobie.project.fuko.database.service;

import de.knoobie.project.clannadutils.bo.DBResult;
import de.knoobie.project.clannadutils.interfaces.DBService;
import de.knoobie.project.fuko.database.domain.Album;
import de.knoobie.project.fuko.database.domain.Artist;
import java.io.Serializable;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class DBServiceAlbum implements DBService<Album>, Serializable {

    private final FukoDB database;

    DBServiceAlbum(final FukoDB database) {
        if (database == null) {
            throw new IllegalArgumentException("Übergabeparameter dürfen nicht null sein (database = "
                    + database + ")!");
        }

        this.database = database;
    }

    @Override
    public DBResult add(Album album) {
        album.setId(getAlbumIDbyVGMDBID(album.getVgmdbID()));
        updateAlbumAndReferences(album);
        return database.update(album);
    }

    private Album updateAlbumAndReferences(Album album) {
        album.getArrangers().replaceAll(database.getArtistService()::getORadd);
        album.getComposers().replaceAll(database.getArtistService()::getORadd);
        album.getLyricists().replaceAll(database.getArtistService()::getORadd);
        album.getPerformers().replaceAll(database.getArtistService()::getORadd);
        album.getEvents().replaceAll(database.getEventService()::getORadd);
        album.getRepresentedProducts().replaceAll(database.getProductService()::getORadd);
        album.getRelatedAlbums().replaceAll(this::getORadd);
        album.getReprints().replaceAll(this::getORadd);
        // Tracks & Cds dürften so gehen?
        // @todo related discs & related products

        return album;
    }

    @Override
    public DBResult update(Album args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DBResult remove(Album args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Album findBy(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Album findBy(int vgmdbID) {
        try {
            CriteriaBuilder criteriaBuilder = database.getEntityManager().getCriteriaBuilder();
            CriteriaQuery cq = criteriaBuilder.createQuery();
            Root<Artist> e = cq.from(Album.class);
            cq.where(criteriaBuilder.equal(e.get("vgmdbID"), criteriaBuilder.parameter(Integer.class, "vgmdbID")));
            Query query = database.getEntityManager().createQuery(cq);
            query.setParameter("vgmdbID", vgmdbID);
            return (Album) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Album getORadd(Album arg) {
        Album realAlbum = findBy(arg.getVgmdbID());
        if (realAlbum == null) {
            database.update(arg);
            realAlbum = findBy(arg.getVgmdbID());
        } 
        return realAlbum;
    }

    public Long getAlbumIDbyVGMDBID(int vgmdbID) {
        try {
            CriteriaBuilder criteriaBuilder = database.getEntityManager().getCriteriaBuilder();
            CriteriaQuery cq = criteriaBuilder.createQuery();
            Root<Artist> e = cq.from(Album.class);
            cq.where(criteriaBuilder.equal(e.get("vgmdbID"), criteriaBuilder.parameter(Integer.class, "vgmdbID")));
            Query query = database.getEntityManager().createQuery(cq);
            query.setParameter("vgmdbID", vgmdbID);
            return ((Album) query.getSingleResult()).getId();
        } catch (NoResultException e) {
            return null;
        }
    }

}
