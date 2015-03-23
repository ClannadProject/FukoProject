package de.knoobie.project.fuko.database.utils;

import de.knoobie.project.clannadutils.common.DateUtilsJ8;
import de.knoobie.project.clannadutils.common.ListUtils;
import de.knoobie.project.clannadutils.common.StringUtils;
import de.knoobie.project.fuko.database.bo.enums.DataType;
import de.knoobie.project.fuko.database.domain.Album;
import de.knoobie.project.fuko.database.domain.Artist;
import de.knoobie.project.fuko.database.domain.ArtistRelease;
import de.knoobie.project.fuko.database.domain.Name;
import de.knoobie.project.fuko.database.domain.VGMdbPicture;
import de.knoobie.project.fuko.database.domain.Website;
import de.knoobie.project.fuko.database.domain.msc.MSCVGMdbEntity;
import de.knoobie.project.fuko.database.domain.msc.MSCVGMdbMeta;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbAlbum;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbArtist;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbDiscography;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbMeta;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbName;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbWebsite;
import de.knoobie.project.nagisa.gson.model.bo.enums.VGMdbArtistType;
import de.knoobie.project.nagisa.gson.model.bo.enums.VGMdbNameLanguage;
import de.knoobie.project.nagisa.gson.model.bo.enums.VGMdbWebsiteType;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class NagisaToFuko {

    public static Album transformVGMdbAlbum(VGMdbAlbum source) {
        if (source == null) {
            return null;
        }

        Album album = new Album();
        album.setAlbumCatalog(StringUtils.trim(source.getCatalog()));
        album.setAlbumType(StringUtils.trim(source.getType()));
        album.setArrangers(null);
        return album;
    }

    public static Artist transformVGMdbArtist(VGMdbArtist source) {
        if (source == null) {
            return null;
        }
        Artist artist = new Artist();
        // clannad update/add
        artist.setCreditedWorks(ListUtils.getListAsString(source.getCreditedWorks()));
        artist.setDescription(StringUtils.trim(source.getDescription()));
        artist.setDiscography(transformVGMdbArtistReleaseToArtistRelease(source.getDiscography()));
        artist.setFeaturedOn(transformVGMdbArtistReleaseToArtistRelease(source.getFeaturedOn()));

//        source.get
//        artist.set
        transformVGMdbLinkToVGMdbID(artist, source.getLink(), DataType.ARTIST);
        // doing Band/Person stuff later
        // setArtistPerson
        // serArtistBand
        // artist.setArtistType(VGMdbArtistType.unit);

        artist.setName(StringUtils.trim(source.getName()));
        artist.setNames(transformVGMdbNames(source.getAliases()));        
        artist.setPicture(transformVGMdbPictureToRealPicture(source.getPicture()));
        artist.setVariations(StringUtils.trim(source.getVariations()));
        transformVGMdbMetaData(artist, source.getMeta());
        artist.setWebsites(transformVGMdbWebsites(source.getWebsites()));
//        artist.set
        
        return artist;
    }
    
    public static void transformVGMdbMetaData(MSCVGMdbMeta newMeta, VGMdbMeta source){
        if(newMeta == null || source == null){
            return;
        }
        
        newMeta.setVgmdbAdded(DateUtilsJ8.getSQLDate(source.getAddedDate(), null, true));
        newMeta.setVgmdbUpdated(DateUtilsJ8.getSQLDate(source.getEditedDate(), null, true));
    }

    public static List<ArtistRelease> transformVGMdbArtistReleaseToArtistRelease(List<VGMdbDiscography> discograhpy) {
        List<ArtistRelease> artistReleases = new ArrayList<>();

        if (ListUtils.isEmpty(discograhpy)) {
            return artistReleases;
        }

        discograhpy.stream().forEach((item) -> {
            ArtistRelease name = new ArtistRelease();
            name.setAlbumCatalog(StringUtils.trim(item.getCatalog()));
            name.setNames(transformVGMdbNames(item.getNames()));
            name.setReleaseDate(DateUtilsJ8.getSQLDate(item.getDate(),
                    DateUtilsJ8.DATE_FORMAT_YEAR_MINUS_MONTH_MINUS_DAY, null, true));
            transformVGMdbLinkToVGMdbID(name, item.getLink(), DataType.ARTIST);
            name.setRoles(ListUtils.getListAsString(item.getRoles()));
        });

        return artistReleases;
    }

    private static void transformVGMdbLinkToVGMdbID(MSCVGMdbEntity entity, String link, DataType type) {
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
    
    private static VGMdbPicture transformVGMdbPictureToRealPicture(de.knoobie.project.nagisa.gson.model.bo.VGMdbPicture source){
        if(source == null){
            return null;
        }
        
        VGMdbPicture picture = new VGMdbPicture();
        picture.setName(StringUtils.trim(source.getName()));
        picture.setUrlFull(StringUtils.trim(source.getFull()));
        picture.setUrlSmall(StringUtils.trim(source.getSmall()));
        picture.setUrlThumbnail(StringUtils.trim(source.getThumbnail()));
        return picture;
    }
    
    private static List<Website> transformVGMdbWebsites(List<VGMdbWebsite> source) {
        List<Website> websites = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return websites;
        }

        source.stream().forEach((sourceWebsite) -> {
            Website website = new Website();
            website.setName(StringUtils.trim(sourceWebsite.getName()));
            website.setLink(StringUtils.trim(sourceWebsite.getLink()));
            website.setWebsiteType(sourceWebsite.getType()== null
                    ? VGMdbWebsiteType.unknown : sourceWebsite.getType());
            websites.add(website);
        });

        return websites;
    }
    
    private static List<Name> transformVGMdbNames(List<VGMdbName> source) {
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
}
