package de.knoobie.project.fuko.database;

import com.google.gson.JsonSyntaxException;
import de.knoobie.project.fuko.database.domain.Album;
import de.knoobie.project.fuko.database.domain.Artist;
import de.knoobie.project.fuko.database.service.FukoDB;
import de.knoobie.project.fuko.database.utils.VGMdbArtistModifier;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbAlbum;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbArtist;
import de.knoobie.project.nagisa.gson.util.TestVGMdb;
import java.io.IOException;

public class Main {

    private static void initDB() {
        System.out.println("Hallo DB");
        System.out.println("DB ---> " + FukoDB.getInstance(FukoDB.PersistenceUnitName.INIT).getEntityManager().isOpen());
    }

    private static void addArtist(int vgmdbID) {
        try {
            VGMdbArtist vgmdbArtist = TestVGMdb.getArtist(""+vgmdbID);

            if (vgmdbArtist != null) {
                System.out.println("vgmdbArtist -> " + vgmdbArtist.getName() + " / " + vgmdbArtist.getLink());
                Artist artist = Artist.getFromVGMDB(vgmdbArtist);
                if (artist != null) {
                    System.out.println("artist -> " + artist.getName() + " / " + artist.getLink());
                    FukoDB.getInstance().getArtistService().add(artist);
                } else {
                    System.out.println("artist -> null");
                }
            } else {
                System.out.println("vgmdbArtist -> null");
            }

//            Artist a = FukoDB.getInstance().getArtistService().findBy(7699);
//            System.out.println("a--->  " + a.getName());
        } catch (IllegalArgumentException | JsonSyntaxException | IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void addAlbum(int vgmdbID) {
        try {
            VGMdbAlbum vgmdbAlbum = TestVGMdb.getAlbum(""+vgmdbID);

            if (vgmdbAlbum != null) {
                System.out.println("vgmdbAlbum -> " + vgmdbAlbum.getName() + " / " + vgmdbAlbum.getLink());
                Album album = Album.getFromVGMDB(vgmdbAlbum);
                if (album != null) {
                    System.out.println("album -> " + album.getName() + " / " + album.getLink());
                    FukoDB.getInstance().getAlbumService().add(album);
                } else {
                    System.out.println("album -> null");
                }
            } else {
                System.out.println("vgmdbAlbum -> null");
            }

//            Artist a = FukoDB.getInstance().g().findBy(45755);
//            System.out.println("a--->  " + a.getName());
        } catch (IllegalArgumentException | JsonSyntaxException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws IllegalArgumentException, JsonSyntaxException, IOException {
        initDB();
        addArtist(6);
        addAlbum(28329);
        addAlbum(5046);
        addArtist(11952);
        addAlbum(45755);
        addAlbum(45756);
        addAlbum(46037);
        addArtist(7699);
        addAlbum(47823);
        addAlbum(551);
        addAlbum(8930);
        
        Artist a = FukoDB.getInstance().getArtistService().findBy(7699);
        System.out.println("---> "+ a.getName());
        a.getDiscography().stream().forEach((release) -> {
            System.out.println("a:discography: "+release.getAlbum().getLink()+" Roles: "+release.getRoles());
        });
        a.getFeaturedOn().stream().forEach((release) -> {
            System.out.println("a:featuredOn: "+release.getAlbum().getLink() +" Roles: "+release.getRoles());
        });

//        VGMdbArtist a = TestVGMdb.getArtist("7699");
//      System.out.println("a--->  "+a.getName());
    }

}
