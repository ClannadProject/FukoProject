package de.knoobie.project.fuko.database.service;

import de.knoobie.project.clannadutils.bo.DBResult;
import de.knoobie.project.clannadutils.interfaces.DBService;
import de.knoobie.project.fuko.database.domain.Artist;
import java.io.Serializable;
import javax.persistence.EntityManager;
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
        DBResult result = new DBResult();

        if (artist == null) {
//            result.setSuccess(false);
//            result.setMessage("Can't add 'no artist'.");
            return result;
        }
        System.out.println("do add");
        System.out.println("database == "+database.toString());
        return database.update(artist);
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
        CriteriaBuilder criteriaBuilder = database.getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = criteriaBuilder.createQuery();
        Root<Artist> e = cq.from(Artist.class);
        cq.where(criteriaBuilder.equal(e.get("vgmdbID"), criteriaBuilder.parameter(Integer.class, "vgmdbID")));
        Query query = database.getEntityManager().createQuery(cq);
        query.setParameter("vgmdbID", vgmdbID);
        return (Artist) query.getSingleResult();
    }
}
