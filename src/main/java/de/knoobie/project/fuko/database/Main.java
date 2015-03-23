package de.knoobie.project.fuko.database;

import com.google.gson.JsonSyntaxException;
import de.knoobie.project.fuko.database.domain.Album;
import de.knoobie.project.fuko.database.domain.Artist;
import de.knoobie.project.fuko.database.service.FukoDB;
import de.knoobie.project.fuko.database.utils.NagisaToFuko;
import de.knoobie.project.nagisa.gson.util.VGMdb;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        // TODO code application logic here
      System.out.println("Hallo DB");
//      System.out.println("Size: --> "+FukoDB.getInstance(FukoDB.PersistenceUnitName.INIT).findAll(Album.class).size());
      
//        Artist artist = new Artist();
//        artist.setName("hans otto");
//        artist.setLink("artist/1233");
//        artist.setVgmdbID(1233);
      
        System.out.println("DB ---> "+ FukoDB.getInstance().getEntityManager().isOpen());
        
        try {
            FukoDB.getInstance().getArtistService().add(NagisaToFuko.transformVGMdbArtist(VGMdb.getArtist("7699")));
            
            Artist a = FukoDB.getInstance().getArtistService().findBy(7699);
            System.out.println("a--->  "+a.getName());
        } catch (IllegalArgumentException | JsonSyntaxException | IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
