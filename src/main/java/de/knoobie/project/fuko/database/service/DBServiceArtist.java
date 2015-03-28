package de.knoobie.project.fuko.database.service;

import de.knoobie.project.fuko.database.domain.Album;
import de.knoobie.project.fuko.database.domain.Artist;
import de.knoobie.project.fuko.database.domain.ArtistRelease;
import de.knoobie.project.fuko.database.domain.Search;
import java.io.Serializable;

public class DBServiceArtist extends AbstractDBService<Artist> implements Serializable {

    DBServiceArtist(final FukoDB database) {
        super(database);
    }

    @Override
    public Artist findBy(int vgmdbID) {
        return findBy(Artist.class, vgmdbID);
    }

    @Override
    protected Artist updateDatabaseRelations(Artist artist) {
        artist.getDiscography().replaceAll(this::getORaddArtistRelease);
        artist.getFeaturedOn().replaceAll(this::getORaddArtistRelease);
        artist.getBandMemberOf().replaceAll(this::getORadd);
        artist.getFormerMember().replaceAll(this::getORadd);
        artist.getMember().replaceAll(this::getORadd);
        return artist;
    }

    private ArtistRelease getORaddArtistRelease(ArtistRelease source) {
        if (source.getAlbum() != null && source.getAlbum().getVgmdbID() != null) {
            Album realAlbum = database.getAlbumService().findBy(source.getAlbum().getVgmdbID());
            if (realAlbum == null) {
                database.getAlbumService().updateDatabaseRelations(source.getAlbum());
                realAlbum = database.getAlbumService().findBy(source.getAlbum().getVgmdbID());
            }
            source.setAlbum(realAlbum);
        }
        return source;
    }

    @Override
    public Search updateSearch(Search arg) {
        arg.getArtists().replaceAll(this::getORadd);
        return arg;
    }

}
