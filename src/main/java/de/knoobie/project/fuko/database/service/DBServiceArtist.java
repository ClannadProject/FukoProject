package de.knoobie.project.fuko.database.service;

import de.knoobie.project.fuko.database.domain.Artist;

public class DBServiceArtist extends AbstractDBService<Artist> {

    DBServiceArtist(final FukoDB database) {
        super(database);
    }

    @Override
    public Artist findBy(int vgmdbID) {
        return findBy(Artist.class, vgmdbID);
    }

    @Override
    protected Artist updateDatabaseRelations(Artist artist) {
        return artist;
    }
}
