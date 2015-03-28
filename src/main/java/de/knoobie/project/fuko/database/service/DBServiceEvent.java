package de.knoobie.project.fuko.database.service;

import de.knoobie.project.clannadutils.bo.DBResult;
import de.knoobie.project.clannadutils.interfaces.DBService;
import de.knoobie.project.fuko.database.domain.Album;
import de.knoobie.project.fuko.database.domain.Artist;
import de.knoobie.project.fuko.database.domain.Event;
import de.knoobie.project.fuko.database.domain.Search;
import java.io.Serializable;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class DBServiceEvent implements DBService<Event, Search>, Serializable {

    private final FukoDB database;

    DBServiceEvent(final FukoDB database) {
        if (database == null) {
            throw new IllegalArgumentException("Übergabeparameter dürfen nicht null sein (database = "
                    + database + ")!");
        }

        this.database = database;
    }

    @Override
    public DBResult add(Event event) {
        event.setId(getEventIDbyVGMDBID(event.getVgmdbID()));
        updateEventAndReferences(event);
        return database.update(event);
    }

    private Event updateEventAndReferences(Event event) {
        event.getReleases().replaceAll(database.getAlbumService()::getORadd);

        return event;
    }

    @Override
    public DBResult update(Event args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DBResult remove(Event args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Event findBy(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Event findBy(int vgmdbID) {
        try {
            CriteriaBuilder criteriaBuilder = database.getEntityManager().getCriteriaBuilder();
            CriteriaQuery cq = criteriaBuilder.createQuery();
            Root<Artist> e = cq.from(Event.class);
            cq.where(criteriaBuilder.equal(e.get("vgmdbID"), criteriaBuilder.parameter(Integer.class, "vgmdbID")));
            Query query = database.getEntityManager().createQuery(cq);
            query.setParameter("vgmdbID", vgmdbID);
            return (Event) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Event getORadd(Event arg) {
        Event realEvent = findBy(arg.getVgmdbID());
        if (realEvent == null) {
            database.update(arg);
            realEvent = findBy(arg.getVgmdbID());
        }
        return realEvent;
    }

    public Long getEventIDbyVGMDBID(int vgmdbID) {
        try {
            CriteriaBuilder criteriaBuilder = database.getEntityManager().getCriteriaBuilder();
            CriteriaQuery cq = criteriaBuilder.createQuery();
            Root<Artist> e = cq.from(Event.class);
            cq.where(criteriaBuilder.equal(e.get("vgmdbID"), criteriaBuilder.parameter(Integer.class, "vgmdbID")));
            Query query = database.getEntityManager().createQuery(cq);
            query.setParameter("vgmdbID", vgmdbID);
            return ((Event) query.getSingleResult()).getId();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Search updateSearch(Search arg) {
        return arg;
    }
}
