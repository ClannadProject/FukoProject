package de.knoobie.project.fuko.database.service;

import de.knoobie.project.fuko.database.domain.Album;
import de.knoobie.project.fuko.database.domain.AlbumDisc;
import de.knoobie.project.fuko.database.domain.embeddable.Picture;
import java.util.List;

public class DBServiceAlbum extends AbstractVGMdbService<Album> {

    DBServiceAlbum(final FukoDB database) {
        super(database);
    }

    @Override
    public Album findByVGMdbID(int vgmdbID) {
        return findBy(Album.class, vgmdbID);
    }

    @Override
    protected Album updateDatabaseRelations(Album album) {
        Album dbAlbum = findByVGMdbID(album.getVgmdbID());

        if (dbAlbum == null) {
            return album;
        }

        if (album.getDiscs().size() == dbAlbum.getDiscs().size()) {
            // same disc size
            int tracksInDBAlbum = 0, tracksInAlbum = 0;
            for (AlbumDisc disc : album.getDiscs()) {
                tracksInAlbum = tracksInAlbum + disc.getTracks().size();
            }
            for (AlbumDisc disc : dbAlbum.getDiscs()) {
                tracksInDBAlbum = tracksInDBAlbum + disc.getTracks().size();
            }

            if (tracksInAlbum == tracksInDBAlbum) {
                album.setDiscs(dbAlbum.getDiscs());
            }

        } else if (album.getDiscs().size() > dbAlbum.getDiscs().size()) {
            // Album got more cds than before  
            System.out.println("New album has more cds!" + album.getVgmdbID());
        }
        return album;
    }

    public Album updatePictures(Album album, List<Picture> newPictures) {
        Album dbAlbum = findByVGMdbID(album.getVgmdbID());

        if (dbAlbum == null) {
            album.setPictures(newPictures);
            return album;
        }
        dbAlbum.setPictures(newPictures);
        return (Album) database.update(dbAlbum).getResult();
    }
    
    public Album updateFileSystem(Album album) {
        Album dbAlbum = findByVGMdbID(album.getVgmdbID());

        if (dbAlbum == null) {
            return album;
        }
        dbAlbum.setPictures(album.getPictures());
        dbAlbum.setFolderLocation(album.getFolderLocation());
        dbAlbum.setFolderName(album.getFolderName());
        dbAlbum.setLocaleStorage(album.isLocaleStorage());
        dbAlbum.setPictures(album.getPictures());
        
        return (Album) database.update(dbAlbum).getResult();        
    }

}
