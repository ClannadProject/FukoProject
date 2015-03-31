package de.knoobie.project.fuko.database.utils;

import de.knoobie.project.clannadutils.common.ListUtils;
import de.knoobie.project.clannadutils.common.StringUtils;
import de.knoobie.project.fuko.database.bo.enums.DataType;
import de.knoobie.project.fuko.database.domain.Album;
import de.knoobie.project.fuko.database.domain.Artist;
import de.knoobie.project.fuko.database.domain.ArtistRelease;
import de.knoobie.project.fuko.database.domain.embeddable.Link;
import de.knoobie.project.fuko.database.domain.Name;
import de.knoobie.project.fuko.database.domain.Picture;
import de.knoobie.project.fuko.database.domain.Website;
import de.knoobie.project.fuko.database.domain.msc.MSCVGMdbEntity;
import de.knoobie.project.fuko.database.domain.msc.MSCVGMdbMeta;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbDiscography;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbEvent;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbMeta;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbName;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbOrganisation;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbPerson;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbPicture;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbWebsite;
import de.knoobie.project.nagisa.gson.model.bo.enums.VGMdbNameLanguage;
import de.knoobie.project.nagisa.gson.model.bo.enums.VGMdbWebsiteType;
import java.util.ArrayList;
import java.util.List;

public class VGMdbCommonModifier {

    public static void updateLink(Link link, List<VGMdbName> names, String _link) {

        if (!StringUtils.isEmpty(_link)) {
            link.setLink(StringUtils.trim(_link));
            link.setVgmdbID(StringUtils.getInteger(
                    StringUtils.trim(_link).replace(
                            link.getType().getType()+ "/",
                            StringUtils.EMPTY), null, true));
        }
//        link.setNames(getModifiedNames(names));
    }

    public static void addVGMdbID(MSCVGMdbEntity entity, String link, DataType type) {
        if (entity == null) {
            return;
        }

        if (StringUtils.isEmpty(link)) {
            entity.setLink(StringUtils.EMPTY);
            entity.setType(type == null ? DataType.UNKNOWN : type);
            return;
        }

        entity.setType(type);
        entity.setVgmdbID(StringUtils.getInteger(
                StringUtils.trim(link).replace(
                        type.getType() + "/",
                        StringUtils.EMPTY), null, true));
        entity.setLink(StringUtils.trim(link));
    }

    public static void addVGMdbMetaData(MSCVGMdbMeta newMeta, VGMdbMeta source) {
        if (newMeta == null || source == null) {
            return;
        }

        newMeta.setVgmdbAdded(StringUtils.trim(source.getAddedDate()));
        newMeta.setVgmdbUpdated(StringUtils.trim(source.getEditedDate()));
    }

    public static List<Name> getModifiedNames(List<VGMdbName> source) {
        List<Name> names = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return names;
        }

        source.stream().forEach((sourceName) -> {
            Name name = new Name();
            name.setName(StringUtils.trim(sourceName.getName()));
            name.setNameLanguage(sourceName.getLanguage() == null
                    ? VGMdbNameLanguage.alias : sourceName.getLanguage());
            names.add(name);
        });

        return names;
    }

    public static List<ArtistRelease> getModifiedArtistsAlbums(Artist artist, List<VGMdbDiscography> discograhpy) {
        List<ArtistRelease> artistReleases = new ArrayList<>();

        if (ListUtils.isEmpty(discograhpy)) {
            return artistReleases;
        }

        discograhpy.stream().forEach((item) -> {
            ArtistRelease release = new ArtistRelease();
            Album album = new Album();
            album.setAlbumCatalog(StringUtils.trim(item.getCatalog()));
            album.setNames(VGMdbCommonModifier.getModifiedNames(item.getNames()));
            album.setReleaseDate(StringUtils.trim(item.getDate()));
            VGMdbCommonModifier.addVGMdbID(album, item.getLink(), DataType.ALBUM);
            release.setAlbum(album);
            release.setRoles(ListUtils.getListAsString(item.getRoles()));
            release.setArtist(artist);
            artistReleases.add(release);
        });

        return artistReleases;
    }

    public static List<Picture> getModifiedPictures(List<VGMdbPicture> source) {
        List<Picture> pictures = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return pictures;
        }

        source.stream().forEach((sourcePicture) -> {
            pictures.add(getModifiedPicture(sourcePicture));
        });

        return pictures;
    }

    public static Picture getModifiedPicture(VGMdbPicture source) {
        if (source == null) {
            return null;
        }

        Picture picture = new Picture();
        picture.setName(StringUtils.trim(source.getName()));
        picture.setUrlFull(StringUtils.trim(source.getFull()));
        picture.setUrlSmall(StringUtils.trim(source.getSmall()));
        picture.setUrlThumbnail(StringUtils.trim(source.getThumbnail()));
        return picture;
    }

    public static List<Website> getModifiedWebsites(List<VGMdbWebsite> source) {
        List<Website> websites = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return websites;
        }

        source.stream().forEach((sourceWebsite) -> {
            Website website = new Website();
            website.setName(StringUtils.trim(sourceWebsite.getName()));
            website.setLink(StringUtils.trim(sourceWebsite.getLink()));
            website.setWebsiteType(sourceWebsite.getType() == null
                    ? VGMdbWebsiteType.unknown : sourceWebsite.getType());
            websites.add(website);
        });

        return websites;
    }

    public static List<Artist> getModifiedPersons(List<VGMdbPerson> source) {
        List<Artist> artists = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return artists;
        }

        source.stream().forEach((person) -> {
            if (!StringUtils.isEmpty(person.getLink())) {
                Artist artist = new Artist();
                artist.setNames(getModifiedNames(person.getNames()));
                addVGMdbID(artist, person.getLink(), DataType.ARTIST);
                artists.add(artist);
            }
        });
        return artists;
    }
}
