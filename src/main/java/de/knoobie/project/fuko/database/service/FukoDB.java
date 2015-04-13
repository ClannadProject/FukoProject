package de.knoobie.project.fuko.database.service;

import de.knoobie.project.fuko.database.bo.DatabaseOperationResult;
import de.knoobie.project.fuko.database.domain.msc.MSCEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;

public class FukoDB {

    public static enum PersistenceUnitName {

        ACTIVE("ACTIVE"), INIT("INIT");

        private final String name;

        PersistenceUnitName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static FukoDB dspDBmanager;

    private final EntityManagerFactory entityManagerFactory;

    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    private FukoDB(PersistenceUnitName persistenceUnit) {
        this.entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit.getName()/**
         * ,
         * FukoDBProperties.databaseProperties*
         */
        );
    }

    public static FukoDB getInstance(PersistenceUnitName persistenceUnit) {
        if (dspDBmanager == null) {
            dspDBmanager = new FukoDB(persistenceUnit);
        }

        return dspDBmanager;
    }

    public static FukoDB getInstance() {
        return getInstance(PersistenceUnitName.ACTIVE);
    }

    private DBServiceAlbum albumService;

    public DBServiceAlbum getAlbumService() {
        if (albumService == null) {
            return albumService = new DBServiceAlbum(this);
        }
        return albumService;
    }

    private DBServiceArtist artistService;

    public DBServiceArtist getArtistService() {
        if (artistService == null) {
            return artistService = new DBServiceArtist(this);
        }
        return artistService;
    }

    private DBServiceProduct productService;

    public DBServiceProduct getProductService() {
        if (productService == null) {
            return productService = new DBServiceProduct(this);
        }
        return productService;
    }

    private DBServiceEvent eventService;

    public DBServiceEvent getEventService() {
        if (eventService == null) {
            return eventService = new DBServiceEvent(this);
        }
        return eventService;
    }

    private DBServiceOrganization organizationService;

    public DBServiceOrganization getOrganizationService() {
        if (organizationService == null) {
            return organizationService = new DBServiceOrganization(this);
        }
        return organizationService;
    }

    public DatabaseOperationResult update(MSCEntity entity) {
        EntityManager entityManager = null;
        DatabaseOperationResult result = new DatabaseOperationResult();
//        result.setResultObj(entity);

        if (entity == null) {
//            result.setMessage("Keine Entity zur Aktualisierung in der Datenbank übergeben!");

            return result;
        }

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            result.setResult(entityManager.merge(entity));
            entityManager.getTransaction().commit();
//            result.setSuccess(true);
        } catch (Exception ex) {
            ex.printStackTrace();
//            result.setMessage("Fehler beim Update einer Entity <" + entity.getClass() + ">. " + ex.getMessage());
//            result.setExp(ex);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return result;
    }

    public <T extends MSCEntity> T findById(Class<? extends MSCEntity> entityClass, final long id) {
        EntityManager entityManager = getEntityManager();

        try {
            return (T) entityManager.find(entityClass, id);
        } catch (NoResultException e) {
            return null;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public List findAll(Class<? extends MSCEntity> entityClass) {
        EntityManager entityManager = getEntityManager();
        CriteriaQuery criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
        criteriaQuery.select(criteriaQuery.from(entityClass));

        try {
            return entityManager.createQuery(criteriaQuery).getResultList();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }
}
