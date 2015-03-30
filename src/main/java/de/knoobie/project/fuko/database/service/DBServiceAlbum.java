package de.knoobie.project.fuko.database.service;

import de.knoobie.project.fuko.database.domain.Album;
import de.knoobie.project.fuko.database.domain.AlbumDisc;
import de.knoobie.project.fuko.database.domain.Picture;
import de.knoobie.project.fuko.database.domain.Search;
import java.io.Serializable;

public class DBServiceAlbum extends AbstractDBService<Album> implements Serializable {

    DBServiceAlbum(final FukoDB database) {
        super(database);
    }

    @Override
    public Album findBy(int vgmdbID) {
        return findBy(Album.class, vgmdbID);
    }

    @Override
    protected Album updateDatabaseRelations(Album album) {
        album.getArrangers().replaceAll(database.getArtistService()::getORadd);
        album.getComposers().replaceAll(database.getArtistService()::getORadd);
        album.getLyricists().replaceAll(database.getArtistService()::getORadd);
        album.getPerformers().replaceAll(database.getArtistService()::getORadd);
        album.getEvents().replaceAll(database.getEventService()::getORadd);
        album.getRepresentedProducts().replaceAll(database.getProductService()::getORadd);
        album.getRelatedAlbums().replaceAll(this::getORadd);
        album.getReprints().replaceAll(this::getORadd);
        return album;
    }

    @Override
    public Search updateSearch(Search object) {
        object.getAlbums().replaceAll(this::getORadd);
        return object;
    }

}
