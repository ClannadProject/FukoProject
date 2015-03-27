package de.knoobie.project.fuko.database;

import com.google.gson.JsonSyntaxException;
import de.knoobie.project.fuko.database.domain.Album;
import de.knoobie.project.fuko.database.domain.Artist;
import de.knoobie.project.fuko.database.domain.Event;
import de.knoobie.project.fuko.database.domain.Name;
import de.knoobie.project.fuko.database.domain.Product;
import de.knoobie.project.fuko.database.service.FukoDB;
import de.knoobie.project.fuko.database.utils.VGMdbArtistModifier;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbAlbum;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbArtist;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbEvent;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbProduct;
import de.knoobie.project.nagisa.gson.util.TestVGMdb;
import java.io.IOException;

public class Main {

    private static void initDB() {
        System.out.println("Hallo DB");
        System.out.println("DB ---> " + FukoDB.getInstance(FukoDB.PersistenceUnitName.INIT).getEntityManager().isOpen());
    }

    private static void addArtist(int vgmdbID) {
        try {
            VGMdbArtist vgmdbArtist = TestVGMdb.getArtist("" + vgmdbID);

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
            VGMdbAlbum vgmdbAlbum = TestVGMdb.getAlbum("" + vgmdbID);

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

        } catch (IllegalArgumentException | JsonSyntaxException | IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void addEvent(int vgmdbID) {
        try {
            VGMdbEvent vgmdbEvent = TestVGMdb.getEvent("" + vgmdbID);

            if (vgmdbEvent != null) {
                System.out.println("vgmdbEvent -> " + vgmdbEvent.getName() + " / " + vgmdbEvent.getLink());
                Event event = Event.getFromVGMDB(vgmdbEvent);
                if (event != null) {
                    System.out.println("event -> " + event.getName() + " / " + event.getLink());
                    FukoDB.getInstance().getEventService().add(event);
                } else {
                    System.out.println("event -> null");
                }
            } else {
                System.out.println("vgmdbEvent -> null");
            }

        } catch (IllegalArgumentException | JsonSyntaxException | IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void addProduct(int vgmdbID) {
        try {
            VGMdbProduct vgmdbproduct = TestVGMdb.getProduct("" + vgmdbID);

            if (vgmdbproduct != null) {
                System.out.println("vgmdbproduct -> " + vgmdbproduct.getName() + " / " + vgmdbproduct.getLink());
                Product product = Product.getFromVGMDB(vgmdbproduct);
                if (product != null) {
                    System.out.println("product -> " + product.getName() + " / " + product.getLink());
                    FukoDB.getInstance().getProductService().add(product);
                } else {
                    System.out.println("product -> null");
                }
            } else {
                System.out.println("vgmdbproduct -> null");
            }

        } catch (IllegalArgumentException | JsonSyntaxException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws IllegalArgumentException, JsonSyntaxException, IOException {
        initDB();
        
        addEvent(1);
        addEvent(98);
        addEvent(54);
        addEvent(175);
        addEvent(171);
        addEvent(123);
//        addProduct(1018);
//        addProduct(1019);
//        addProduct(1020);
//        addProduct(1021);
//        addProduct(1022);
//        addProduct(1023);
//                addArtist(6);
//        addAlbum(28329);
//        addAlbum(5046);
//        addArtist(11952);
//        addAlbum(45755);
//        addAlbum(45756);
//        addAlbum(46037);
//        addArtist(7699);
//        addAlbum(47823);
//        addAlbum(551);
//        addAlbum(8930);

//        Product a = FukoDB.getInstance().getProductService().findBy(1018);
//        System.out.println("Product: "+ a.getName());
//        a.getNames().stream().forEach((_item) -> {
//            System.out.println("Name: "+_item.getName()+" Language "+_item.getNameLanguage().getHumanizedName());
//        });
//        System.out.println("Franchise:");
//        a.getFranchises().stream().forEach((_item) -> {
//            System.out.println("Franchise: "+_item.getName()+" Link "+_item.getLink());
//        });
//        System.out.println("Title:");
//        a.getTitles().stream().forEach((_item) -> {
//            System.out.println("Title: "+_item.getName()+" Link "+_item.getLink());
//        });
//        System.out.println("Alben:");
//        a.getRelatedAlbums().stream().forEach((_item) -> {
//            System.out.println("Album: "+_item.getName()+" Link "+_item.getLink());
//        });
//        addProduct(1018);
//        Artist a = FukoDB.getInstance().getArtistService().findBy(7699);
//        System.out.println("---> "+ a.getName());
//        a.getDiscography().stream().forEach((release) -> {
//            System.out.println("a:discography: "+release.getAlbum().getLink()+" Roles: "+release.getRoles());
//        });
//        a.getFeaturedOn().stream().forEach((release) -> {
//            System.out.println("a:featuredOn: "+release.getAlbum().getLink() +" Roles: "+release.getRoles());
//        });
//        VGMdbArtist a = TestVGMdb.getArtist("7699");
//      System.out.println("a--->  "+a.getName());
    }

}
