package de.knoobie.project.fuko.database.service;

import de.knoobie.project.clannadutils.bo.DBResult;
import de.knoobie.project.fuko.database.domain.msc.MSCClannadMeta;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public abstract class AbstractDBService<T extends MSCClannadMeta> {

    private final FukoDB database;
    private static final String VGMDB_COLUMN_ID = "vgmdbID";

    AbstractDBService(final FukoDB database) {
        if (database == null) {
            throw new IllegalArgumentException("Übergabeparameter dürfen nicht null sein (database = "
                    + database + ")!");
        }

        this.database = database;
    }

//    public DBResult add(T args){
//        
//    };
    public T getORadd(T source) {
        T realObject = findBy(source.getVgmdbID());
        if (realObject == null) {
            database.update(source);
            realObject = findBy(source.getVgmdbID());
        }
        return realObject;
    }

    public Long getID(int vgmdbID) {
        T databaseObject = findBy(vgmdbID);
        if (databaseObject != null && databaseObject.getId() != null) {
            return databaseObject.getId();
        }
        return null;
    }

    public T findBy(T clazz, int vgmdbID) {
        try {
            CriteriaBuilder criteriaBuilder = database.getEntityManager().getCriteriaBuilder();
            CriteriaQuery cq = criteriaBuilder.createQuery();
            Root<T> e = cq.from(clazz.getClass());
            cq.where(criteriaBuilder.equal(e.get(VGMDB_COLUMN_ID), criteriaBuilder.parameter(Integer.class, VGMDB_COLUMN_ID)));
            Query query = database.getEntityManager().createQuery(cq);
            query.setParameter(VGMDB_COLUMN_ID, vgmdbID);
            return (T) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public DBResult update(T object) {
        object.setId(getID(object.getVgmdbID()));
        object = updateDatabaseRelations(object);
        return database.update(object);
    }

    public abstract T findBy(int vgmdbID);

    public abstract T updateDatabaseRelations(T object);
}
