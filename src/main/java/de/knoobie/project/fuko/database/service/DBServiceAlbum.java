package de.knoobie.project.fuko.database.service;

import de.knoobie.project.fuko.database.domain.Album;

public class DBServiceAlbum extends AbstractDBService<Album> {

    DBServiceAlbum(final FukoDB database) {
        super(database);
    }

    @Override
    public Album findBy(int vgmdbID) {
        return findBy(Album.class, vgmdbID);
    }

    @Override
    protected Album updateDatabaseRelations(Album album) {
        Album dbAlbum = findBy(album.getVgmdbID());
        if (dbAlbum != null) {
            dbAlbum.getDiscs().clear();
            database.update(dbAlbum);
        }
        return album;
    }


}
