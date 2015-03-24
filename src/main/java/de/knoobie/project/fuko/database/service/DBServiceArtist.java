package de.knoobie.project.fuko.database.service;

import de.knoobie.project.clannadutils.bo.DBResult;
import de.knoobie.project.clannadutils.interfaces.DBService;
import de.knoobie.project.fuko.database.domain.Album;
import de.knoobie.project.fuko.database.domain.Artist;
import de.knoobie.project.fuko.database.domain.ArtistRelease;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class DBServiceArtist implements DBService<Artist>, Serializable {

    private final FukoDB database;

    DBServiceArtist(final FukoDB database) {
        if (database == null) {
            throw new IllegalArgumentException("Übergabeparameter dürfen nicht null sein (database = "
                    + database + ")!");
        }

        this.database = database;
    }

    private Artist synchronizeArtist(Artist artist) {

        return new Artist();
    }

    @Override
    public DBResult add(Artist artist) {
        return database.update(updateArtistAndReferences(artist));
    }

    @Override
    public Artist getORadd(Artist source) {
        Artist realArtist = database.getArtistService().findBy(source.getVgmdbID());
        if (realArtist == null) {
            database.getArtistService().add(source);
            realArtist = database.getArtistService().findBy(source.getVgmdbID());
        }
        return realArtist;
    }

    private ArtistRelease getORaddArtistRelease(ArtistRelease source) {
        if (source.getAlbum() != null && source.getAlbum().getVgmdbID() != null) {
            Album realAlbum = database.getAlbumService().findBy(source.getAlbum().getVgmdbID());
            if (realAlbum == null) {
                database.getAlbumService().add(source.getAlbum());
                realAlbum = database.getAlbumService().findBy(source.getAlbum().getVgmdbID());
            }
            source.setAlbum(realAlbum);
        }
        return source;
    }

    private Artist updateArtistAndReferences(Artist artist) {
        artist.getDiscography().replaceAll(this::getORaddArtistRelease);
        artist.getFeaturedOn().replaceAll(this::getORaddArtistRelease);
        artist.getBandMemberOf().replaceAll(this::getORadd);
        artist.getFormerMember().replaceAll(this::getORadd);
        artist.getMember().replaceAll(this::getORadd);

        return artist;
    }

    @Override
    public DBResult update(Artist args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DBResult remove(Artist args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Artist findBy(long id) {
        EntityManager entityManager = database.getEntityManager();

        try {
            return (Artist) entityManager.find(Artist.class, id);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

    }

    @Override
    public Artist findBy(int vgmdbID) {
        try {
            CriteriaBuilder criteriaBuilder = database.getEntityManager().getCriteriaBuilder();
            CriteriaQuery cq = criteriaBuilder.createQuery();
            Root<Artist> e = cq.from(Artist.class);
            cq.where(criteriaBuilder.equal(e.get("vgmdbID"), criteriaBuilder.parameter(Integer.class, "vgmdbID")));
            Query query = database.getEntityManager().createQuery(cq);
            query.setParameter("vgmdbID", vgmdbID);
            return (Artist) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
