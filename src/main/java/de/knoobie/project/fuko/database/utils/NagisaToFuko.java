package de.knoobie.project.fuko.database.utils;

import de.knoobie.project.clannadutils.common.DateUtils;
import de.knoobie.project.clannadutils.common.ListUtils;
import de.knoobie.project.clannadutils.common.StringUtils;
import de.knoobie.project.fuko.database.bo.enums.DataType;
import de.knoobie.project.fuko.database.domain.Album;
import de.knoobie.project.fuko.database.domain.Artist;
import de.knoobie.project.fuko.database.domain.ArtistRelease;
import de.knoobie.project.fuko.database.domain.Name;
import de.knoobie.project.fuko.database.domain.Organisation;
import de.knoobie.project.fuko.database.domain.Product;
import de.knoobie.project.fuko.database.domain.Store;
import de.knoobie.project.fuko.database.domain.VGMdbPicture;
import de.knoobie.project.fuko.database.domain.Website;
import de.knoobie.project.fuko.database.domain.msc.MSCVGMdbEntity;
import de.knoobie.project.fuko.database.domain.msc.MSCVGMdbMeta;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbAlbum;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbAlbumReprint;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbArtist;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbDiscography;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbMeta;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbName;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbOrganisation;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbPerson;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbProduct;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbStore;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbWebsite;
import de.knoobie.project.nagisa.gson.model.bo.enums.VGMdbNameLanguage;
import de.knoobie.project.nagisa.gson.model.bo.enums.VGMdbWebsiteType;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class NagisaToFuko {

    public static Album transformVGMdbAlbum(VGMdbAlbum source) {
        if (source == null) {
            return null;
        }

        Album album = new Album();
        transformVGMdbLinkToVGMdbID(album, source.getLink(), DataType.ALBUM);
        album.setArrangers(transformVGMdbPersons(source.getArrangers()));
        album.setAlbumCatalog(StringUtils.trim(source.getCatalog()));
        album.setAlbumType(StringUtils.trim(source.getType()));
        album.setClannadAdded(new Date(DateUtils.getNow().getTime()));
        album.setClannadUpdated(album.getClannadAdded());
        album.setClassification(StringUtils.trim(source.getClassification()));
        album.setComposers(transformVGMdbPersons(source.getComposers()));
        album.setCover(transformVGMdbPictureToRealPicture(source.getPicture()));
        album.setDescription(StringUtils.trim(source.getDescription()));
        // album.setDisc
        album.setDistributor(_transformVGMdbOrganisation(source.getDistributor()));
        album.setPublisher(_transformVGMdbOrganisation(source.getPublisher()));
        album.setLyricists(transformVGMdbPersons(source.getLyricists()));
        album.setPerformers(transformVGMdbPersons(source.getPerformers()));
        album.setMediaFormat(StringUtils.trim(source.getMediaFormat()));
        album.setPlatforms(ListUtils.getListAsString(source.getPlatforms()));
        album.setRelatedAlbums(_transformVGMDBAlbumList(source.getRelatedAlbums()));
        album.setPictures(transformVGMdbPicturesToRealPictures(source.getPictures()));
        album.setReleaseCurrency(source.getRelease() == null ? StringUtils.EMPTY
                : StringUtils.trim(source.getRelease().getCurrency()));
        album.setReleaseDate(StringUtils.trim(source.getReleaseDate()));
        album.setReleaseFormat(StringUtils.trim(source.getPublishFormat()));
        album.setReleasePrice(source.getRelease() == null || source.getRelease().getPrice() == null
                ? 0.0 : source.getRelease().getPrice());
        album.setReprint(album.getReprint());
        album.setReprints(_transformVGMDBAlbumReprintList(source.getReprints()));
        album.setRepresentedProducts(_transformVGMDBProductList(source.getProducts()));
        album.setStores(_transformVGMDBStoreList(source.getStores()));
        return album;
    }

    private static List<Store> _transformVGMDBStoreList(List<VGMdbStore> source) {
        List<Store> stores = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return stores;
        }

        source.stream().forEach((sourceStore) -> {
            Store store = new Store();
            store.setName(sourceStore.getName());
            store.setLink(StringUtils.trim(sourceStore.getLink()));
            stores.add(store);

        });

        return stores;
    }

    private static List<Product> _transformVGMDBProductList(List<VGMdbProduct> source) {
        List<Product> products = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return products;
        }

        source.stream().forEach((sourceProduct) -> {
            if (!StringUtils.isEmpty(sourceProduct.getLink())) {
                Product product = new Product();
                product.setNames(transformVGMdbNames(sourceProduct.getNames()));
                transformVGMdbLinkToVGMdbID(product, sourceProduct.getLink(), DataType.PRODUCT);
                products.add(product);
            }
        });

        return products;
    }

    private static List<Album> _transformVGMDBAlbumReprintList(List<VGMdbAlbumReprint> source) {
        List<Album> albums = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return albums;
        }

        source.stream().forEach((sourceAlbum) -> {
            if (!StringUtils.isEmpty(sourceAlbum.getLink())) {
                Album album = new Album();
                album.setName(StringUtils.trim(sourceAlbum.getName()));
                album.setAlbumCatalog(StringUtils.trim(sourceAlbum.getCatalog()));
                transformVGMdbLinkToVGMdbID(album, sourceAlbum.getLink(), DataType.ALBUM);
                albums.add(album);
            }
        });

        return albums;
    }

    private static List<Album> _transformVGMDBAlbumList(List<VGMdbAlbum> source) {
        List<Album> albums = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return albums;
        }

        source.stream().forEach((sourceAlbum) -> {
            if (!StringUtils.isEmpty(sourceAlbum.getLink())) {
                Album album = new Album();
                album.setNames(transformVGMdbNames(sourceAlbum.getNames()));
                album.setAlbumCatalog(StringUtils.trim(sourceAlbum.getCatalog()));
                album.setReleaseDate(StringUtils.trim(sourceAlbum.getReleaseDate()));
                transformVGMdbLinkToVGMdbID(album, sourceAlbum.getLink(), DataType.ALBUM);
                albums.add(album);
            }
        });

        return albums;
    }

    private static Organisation _transformVGMdbOrganisation(VGMdbOrganisation source) {
        if (source == null) {
            return null;
        }

        Organisation organisation = new Organisation();
        organisation.setName(StringUtils.trim(source.getName()));
        organisation.setNames(transformVGMdbNames(source.getAliases()));
        transformVGMdbLinkToVGMdbID(organisation, source.getLink(), DataType.ORGANISATION);

        return organisation;
    }

    public static List<Artist> transformVGMdbPersons(List<VGMdbPerson> source) {
        List<Artist> artists = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return artists;
        }

        source.stream().forEach((person) -> {
            if (!StringUtils.isEmpty(person.getLink())) {
                Artist artist = new Artist();
                artist.setNames(transformVGMdbNames(person.getNames()));
                transformVGMdbLinkToVGMdbID(artist, person.getLink(), DataType.ARTIST);
                artists.add(artist);
            }
        });
        return artists;
    }

    public static Artist transformVGMdbArtist(VGMdbArtist source) {
        if (source == null) {
            return null;
        }
        Artist artist = new Artist();
        // clannad update/add
        artist.setCreditedWorks(ListUtils.getListAsString(source.getCreditedWorks()));
        artist.setClannadAdded(new Date(DateUtils.getNow().getTime()));
        artist.setClannadUpdated(artist.getClannadAdded());
        artist.setDescription(StringUtils.trim(source.getDescription()));
        transformVGMdbLinkToVGMdbID(artist, source.getLink(), DataType.ARTIST);
        artist.setDiscography(transformVGMdbArtistReleaseToArtistRelease(artist, source.getDiscography()));
        artist.setFeaturedOn(transformVGMdbArtistReleaseToArtistRelease(artist, source.getFeaturedOn()));

        if (source.getType() != null) {
            artist.setArtistType(source.getType());
            switch (source.getType()) {
                case individual:
                    if (source.getPersonInfo() != null) {
                        artist.setBirthdate(StringUtils.trim(source.getPersonInfo().getBirthdate()));
                        artist.setBirthplace(StringUtils.trim(source.getPersonInfo().getBirthplace()));
                        artist.setBloodtype(StringUtils.trim(source.getPersonInfo().getBloodtype()));
                        artist.setGender(source.getPersonInfo().getGender());
                        artist.setBandMemberOf(transformVGMdbPersons(source.getPersonInfo().getBandMemberOf()));
                    }
                    break;
                case unit:
                    if (source.getBandInfo() != null) {
                        artist.setFormed(StringUtils.trim(source.getBandInfo().getFormed()));
                        artist.setMember(transformVGMdbPersons(source.getBandInfo().getMember()));
                        artist.setFormerMember(transformVGMdbPersons(source.getBandInfo().getFormerMember()));
                    }
                    break;
            }
        }

        artist.setName(StringUtils.trim(source.getName()));
        artist.setNames(transformVGMdbNames(source.getAliases()));
        artist.setPicture(transformVGMdbPictureToRealPicture(source.getPicture()));
        artist.setVariations(StringUtils.trim(source.getVariations()));
        transformVGMdbMetaData(artist, source.getMeta());
        artist.setWebsites(transformVGMdbWebsites(source.getWebsites()));

        return artist;
    }

//    public static List<Artist> transformListOfVGMdbPersonToListOfArtist(List<VGMdbPerson> source) {
//        List<Artist> artists = new ArrayList<>();
//
//        if (ListUtils.isEmpty(source)) {
//            return artists;
//        }
//
//        source.stream().forEach((person) -> {
//            if (!StringUtils.isEmpty(person.getLink())) {
//                Artist artist = new Artist();
//
//                artist.setNames(transformVGMdbNames(person.getNames()));
//                transformVGMdbLinkToVGMdbID(artist, person.getLink(), DataType.ARTIST);
//
//                artists.add(artist);
//            }
//
//        });
//
//        return artists;
//    }
    public static void transformVGMdbMetaData(MSCVGMdbMeta newMeta, VGMdbMeta source) {
        if (newMeta == null || source == null) {
            return;
        }

        newMeta.setVgmdbAdded(StringUtils.trim(source.getAddedDate()));
        newMeta.setVgmdbUpdated(StringUtils.trim(source.getEditedDate()));
    }

    public static List<ArtistRelease> transformVGMdbArtistReleaseToArtistRelease(Artist artist, List<VGMdbDiscography> discograhpy) {
        List<ArtistRelease> artistReleases = new ArrayList<>();

        if (ListUtils.isEmpty(discograhpy)) {
            return artistReleases;
        }

        discograhpy.stream().forEach((item) -> {
            ArtistRelease release = new ArtistRelease();
            Album album = new Album();
            album.setAlbumCatalog(StringUtils.trim(item.getCatalog()));
            album.setNames(transformVGMdbNames(item.getNames()));
            album.setReleaseDate(StringUtils.trim(item.getDate()));
            transformVGMdbLinkToVGMdbID(album, item.getLink(), DataType.ALBUM);
            release.setAlbum(album);
            release.setRoles(ListUtils.getListAsString(item.getRoles()));
            release.setArtist(artist);
            artistReleases.add(release);
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

    private static List<VGMdbPicture> transformVGMdbPicturesToRealPictures(List<de.knoobie.project.nagisa.gson.model.bo.VGMdbPicture> source) {
        List<VGMdbPicture> pictures = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return pictures;
        }

        source.stream().forEach((sourcePicture) -> {
            pictures.add(transformVGMdbPictureToRealPicture(sourcePicture));
        });

        return pictures;
    }

    private static VGMdbPicture transformVGMdbPictureToRealPicture(de.knoobie.project.nagisa.gson.model.bo.VGMdbPicture source) {
        if (source == null) {
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
            website.setWebsiteType(sourceWebsite.getType() == null
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
