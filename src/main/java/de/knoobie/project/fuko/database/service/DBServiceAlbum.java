package de.knoobie.project.fuko.database.service;

import de.knoobie.project.clannadutils.bo.DBResult;
import de.knoobie.project.clannadutils.interfaces.DBService;
import de.knoobie.project.fuko.database.domain.Album;
import de.knoobie.project.fuko.database.domain.Artist;
import java.io.Serializable;

public class DBServiceAlbum implements DBService<Album>, Serializable {

    private final FukoDB database;

    DBServiceAlbum(final FukoDB database) {
        if (database == null) {
            throw new IllegalArgumentException("Übergabeparameter dürfen nicht null sein (database = "
                    + database + ")!");
        }

        this.database = database;
    }
    
    private Artist synchronizeArtist(Artist artist){
        
        return new Artist();
    }

    @Override
    public DBResult add(Album album) {
        DBResult result = new DBResult();

        if (album == null) {
//            result.setSuccess(false);
//            result.setMessage("Can't add 'no album'.");
            return result;
        }
        
        

        return result;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
