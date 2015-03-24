package de.knoobie.project.fuko.database;

import com.google.gson.JsonSyntaxException;
import de.knoobie.project.fuko.database.domain.Artist;
import de.knoobie.project.fuko.database.service.FukoDB;
import de.knoobie.project.fuko.database.utils.NagisaToFuko;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbArtist;
import de.knoobie.project.nagisa.gson.util.TestVGMdb;
import de.knoobie.project.nagisa.gson.util.VGMdb;
import java.io.IOException;
import javax.persistence.NoResultException;

public class Main {

    public static void main(String[] args) throws IllegalArgumentException, JsonSyntaxException, IOException {
        // TODO code application logic here
      System.out.println("Hallo DB");

//        VGMdbArtist a = TestVGMdb.getArtist("7699");
//      System.out.println("a--->  "+a.getName());
      
      
        System.out.println("DB ---> "+ FukoDB.getInstance(FukoDB.PersistenceUnitName.INIT).getEntityManager().isOpen());
        
        try {
            VGMdbArtist vgmdbArtist = TestVGMdb.getArtist("7699");
            
            try {
              Artist dbArtist = FukoDB.getInstance().getArtistService().findBy(1);
              System.out.println("--> Artist 1: " + dbArtist);
            } catch (NoResultException nrEx) {
              System.out.println("--> Exception: " + nrEx);
            }
            
            if(vgmdbArtist != null){
                System.out.println("vgmdbArtist -> "+ vgmdbArtist.getName()+ " / "+ vgmdbArtist.getLink());
                Artist artist = NagisaToFuko.transformVGMdbArtist(vgmdbArtist);
                if(artist != null){
                System.out.println("artist -> "+ artist.getName()+ " / "+ artist.getLink());
                FukoDB.getInstance().getArtistService().add(artist);
                }else{
                    System.out.println("artist -> null");
                }
            }else{
                System.out.println("vgmdbArtist -> null");
            }
            
            Artist a = FukoDB.getInstance().getArtistService().findBy(7699);
            System.out.println("a--->  "+a.getName());
        } catch (IllegalArgumentException | JsonSyntaxException | IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
