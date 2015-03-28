package de.knoobie.project.fuko.database.service;

import de.knoobie.project.clannadutils.bo.DBResult;
import de.knoobie.project.clannadutils.interfaces.DBService;
import de.knoobie.project.fuko.database.domain.Artist;
import de.knoobie.project.fuko.database.domain.Organization;
import de.knoobie.project.fuko.database.domain.OrganizationRelease;
import de.knoobie.project.fuko.database.domain.Search;
import java.io.Serializable;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class DBServiceOrganization implements DBService<Organization, Search>, Serializable {

    private final FukoDB database;

    DBServiceOrganization(final FukoDB database) {
        if (database == null) {
            throw new IllegalArgumentException("Übergabeparameter dürfen nicht null sein (database = "
                    + database + ")!");
        }

        this.database = database;
    }

    @Override
    public DBResult add(Organization org) {
        org.setId(getOrganisationIDbyVGMDBID(org.getVgmdbID()));
        updateOrganisationAndReferences(org);
        return database.update(org);
    }

    private Organization updateOrganisationAndReferences(Organization event) {
        event.getReleases().replaceAll(this::getORaddRelease);

        return event;
    }

    @Override
    public DBResult update(Organization args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DBResult remove(Organization args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Organization findBy(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Organization findBy(int vgmdbID) {
        try {
            CriteriaBuilder criteriaBuilder = database.getEntityManager().getCriteriaBuilder();
            CriteriaQuery cq = criteriaBuilder.createQuery();
            Root<Artist> e = cq.from(Organization.class);
            cq.where(criteriaBuilder.equal(e.get("vgmdbID"), criteriaBuilder.parameter(Integer.class, "vgmdbID")));
            Query query = database.getEntityManager().createQuery(cq);
            query.setParameter("vgmdbID", vgmdbID);
            return (Organization) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public OrganizationRelease getORaddRelease(OrganizationRelease arg) {

        if (arg.getAlbum() != null) {
            arg.setAlbum(database.getAlbumService().getORadd(arg.getAlbum()));
        }

        if (arg.getReleaseEvent() != null) {
            arg.setReleaseEvent(database.getEventService().getORadd(arg.getReleaseEvent()));
        }
        return arg;
    }

    @Override
    public Organization getORadd(Organization arg) {
        Organization realOrganisation = findBy(arg.getVgmdbID());
        if (realOrganisation == null) {
            database.update(arg);
            realOrganisation = findBy(arg.getVgmdbID());
        }
        return realOrganisation;
    }

    public Long getOrganisationIDbyVGMDBID(int vgmdbID) {
        try {
            CriteriaBuilder criteriaBuilder = database.getEntityManager().getCriteriaBuilder();
            CriteriaQuery cq = criteriaBuilder.createQuery();
            Root<Artist> e = cq.from(Organization.class);
            cq.where(criteriaBuilder.equal(e.get("vgmdbID"), criteriaBuilder.parameter(Integer.class, "vgmdbID")));
            Query query = database.getEntityManager().createQuery(cq);
            query.setParameter("vgmdbID", vgmdbID);
            return ((Organization) query.getSingleResult()).getId();
        } catch (NoResultException e) {
            return null;
        }
    }    
    
    @Override
    public Search updateSearch(Search arg) {
        arg.getOrganizations().replaceAll(this::getORadd);
        return arg;
    }

}
