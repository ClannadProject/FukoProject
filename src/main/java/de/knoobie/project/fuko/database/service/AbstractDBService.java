package de.knoobie.project.fuko.database.service;

import de.knoobie.project.clannadutils.common.DateUtils;
import de.knoobie.project.fuko.database.bo.DatabaseOperationResult;
import de.knoobie.project.fuko.database.domain.msc.MSCClannadMeta;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public abstract class AbstractDBService<T extends MSCClannadMeta> implements Serializable {

    public final FukoDB database;
    public static final String VGMDB_COLUMN_ID = "vgmdbID";

    AbstractDBService(final FukoDB database) {
        if (database == null) {
            throw new IllegalArgumentException("Übergabeparameter dürfen nicht null sein (database = "
                    + database + ")!");
        }

        this.database = database;
    }

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

    public T findBy(Class<T> clazz, int vgmdbID) {
        try {
            CriteriaBuilder criteriaBuilder = database.getEntityManager().getCriteriaBuilder();
            CriteriaQuery cq = criteriaBuilder.createQuery();
            Root<T> e = cq.from(clazz);
            cq.where(criteriaBuilder.equal(e.get(VGMDB_COLUMN_ID), criteriaBuilder.parameter(Integer.class, VGMDB_COLUMN_ID)));
            Query query = database.getEntityManager().createQuery(cq);
            query.setParameter(VGMDB_COLUMN_ID, vgmdbID);
            return (T) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public DatabaseOperationResult updateWithRelations(T object, boolean forceUpdate) {
        T dbObject = findBy(object.getVgmdbID());
        if (dbObject != null) {
            object.setId(dbObject.getId());
            object.setClannadAdded(dbObject.getClannadAdded());
            object.setClannadUpdated(new Timestamp(DateUtils.getTimestampNow().getTime()));
        } else {
            if (object.getClannadAdded() == null) {
                object.setClannadAdded(new Timestamp(DateUtils.getTimestampNow().getTime()));
            }
        }

        object = updateDatabaseRelations(object);
        return database.update(object);
    }

    public abstract T findBy(int vgmdbID);

    protected abstract T updateDatabaseRelations(T object);
}
