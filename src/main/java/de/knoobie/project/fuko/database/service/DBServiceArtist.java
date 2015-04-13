package de.knoobie.project.fuko.database.service;

import de.knoobie.project.fuko.database.domain.Artist;

public class DBServiceArtist extends AbstractVGMdbService<Artist> {

    DBServiceArtist(final FukoDB database) {
        super(database);
    }

    @Override
    public Artist findByVGMdbID(int vgmdbID) {
        return findBy(Artist.class, vgmdbID);
    }

    @Override
    protected Artist updateDatabaseRelations(Artist artist) {
        return artist;
    }

    public Artist updateFileSystem(Artist artist) {
        Artist dbArtist = findByVGMdbID(artist.getVgmdbID());

        if (dbArtist == null) {
            return artist;
        }
        dbArtist.setPicture(artist.getPicture());
        dbArtist.setFolderLocation(artist.getFolderLocation());
        dbArtist.setFolderName(artist.getFolderName());
        dbArtist.setLocaleStorage(artist.isLocaleStorage());

        return (Artist) database.update(dbArtist).getResult();
    }
}
