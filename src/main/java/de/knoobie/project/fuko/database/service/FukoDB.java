package de.knoobie.project.fuko.database.service;

import de.knoobie.project.clannadutils.bo.DBResult;
import de.knoobie.project.fuko.database.domain.msc.MSCEntity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
        this.entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit.getName());
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

    public DBResult update(MSCEntity entity) {
        EntityManager entityManager = null;
        DBResult result = new DBResult();
//        result.setResultObj(entity);

        if (entity == null) {
//            result.setMessage("Keine Entity zur Aktualisierung in der Datenbank übergeben!");

            return result;
        }

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(entity);
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
}
