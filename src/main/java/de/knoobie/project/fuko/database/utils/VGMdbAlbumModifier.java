package de.knoobie.project.fuko.database.utils;

import de.knoobie.project.clannadutils.common.DateUtils;
import de.knoobie.project.clannadutils.common.ListUtils;
import de.knoobie.project.clannadutils.common.StringUtils;
import de.knoobie.project.fuko.database.bo.enums.DataType;
import de.knoobie.project.fuko.database.domain.Album;
import de.knoobie.project.fuko.database.domain.AlbumDisc;
import de.knoobie.project.fuko.database.domain.AlbumTrack;
import de.knoobie.project.fuko.database.domain.Organization;
import de.knoobie.project.fuko.database.domain.OrganizationRelease;
import de.knoobie.project.fuko.database.domain.Store;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbAlbum;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbAlbumDisc;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbAlbumReprint;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbAlbumTrack;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbEventRelease;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbOrganisationRelease;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbStore;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class VGMdbAlbumModifier {

    public static List<Album> transformEventReleaseList(List<VGMdbEventRelease> source) {
        List<Album> albums = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return albums;
        }

        source.stream().forEach((sourceAlbum) -> {
            if (!StringUtils.isEmpty(sourceAlbum.getLink())) {
                albums.add(transformVGMdbEventRelease(sourceAlbum));
            }
        });

        return albums;
    }

    public static Album transformVGMdbEventRelease(VGMdbEventRelease source) {
        if (source == null) {
            return null;
        }

        Album album = new Album();
        album.setReleaseDate(StringUtils.trim(source.getReleaseDate()));
        album.setAlbumType(StringUtils.trim(source.getAlbumType()));
        album.setNames(VGMdbCommonModifier.getModifiedNames(source.getNames()));
        album.setAlbumCatalog(StringUtils.trim(source.getCatalog()));
        album.setPublisher(VGMdbOrganizationModifier.transformVGMdbPersonToRelease(album, source.getPublisher(), "Publisher"));
        VGMdbCommonModifier.addVGMdbID(album, source.getLink(), DataType.ALBUM);

        return album;
    }

    public static List<Album> transformAlbumList(List<VGMdbAlbum> source) {
        List<Album> albums = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return albums;
        }

        source.stream().forEach((sourceAlbum) -> {
            if (!StringUtils.isEmpty(sourceAlbum.getLink())) {
                albums.add(transformVGMdbAlbum(sourceAlbum, true));
            }
        });

        return albums;
    }

    public static Album transformVGMdbAlbum(VGMdbAlbum source, boolean incompleteSource) {
        if (source == null) {
            return null;
        }

        Album album = new Album();

        album.setCover(VGMdbCommonModifier.getModifiedPicture(source.getPicture()));
        album.setReleaseDate(StringUtils.trim(source.getReleaseDate()));
        album.setAlbumType(StringUtils.trim(source.getType()));
        album.setName(StringUtils.trim(source.getName()));
        album.setNames(VGMdbCommonModifier.getModifiedNames(source.getNames()));
        album.setAlbumCatalog(StringUtils.trim(source.getCatalog()));
        album.setClassification(StringUtils.trim(source.getClassification()));
        album.setPublisher(VGMdbOrganizationModifier.transformVGMdbOrganisationToRelease(album, source.getPublisher(), "Publisher"));

        VGMdbCommonModifier.addVGMdbID(album, source.getLink(), DataType.ALBUM);

        if (incompleteSource) {
            return album;
        }

        album.setArrangers(VGMdbCommonModifier.getModifiedPersons(source.getArrangers()));
        album.setComposers(VGMdbCommonModifier.getModifiedPersons(source.getComposers()));
        album.setLyricists(VGMdbCommonModifier.getModifiedPersons(source.getLyricists()));
        album.setPerformers(VGMdbCommonModifier.getModifiedPersons(source.getPerformers()));
        album.setEvents(VGMdbEventModifier.transformEventList(source.getEvents()));
        album.setVgmdbLink(StringUtils.trim(source.getVgmdbLink()));

        album.setPictures(VGMdbCommonModifier.getModifiedPictures(source.getPictures()));

        album.setDistributor(VGMdbOrganizationModifier.transformVGMdbOrganisation(source.getDistributor(), true));

        album.setDiscs(getModifiedAlbumDisc(source.getDiscs(), album));

        album.setDescription(StringUtils.trim(source.getDescription()));
        album.setMediaFormat(StringUtils.trim(source.getMediaFormat()));
        album.setClannadAdded(new Date(DateUtils.getNow().getTime()));
        album.setClannadUpdated(album.getClannadAdded());

        album.setPlatforms(ListUtils.getListAsString(source.getPlatforms()));
        album.setRelatedAlbums(transformAlbumList(source.getRelatedAlbums()));
        album.setReleaseCurrency(source.getRelease() == null ? StringUtils.EMPTY
                : StringUtils.trim(source.getRelease().getCurrency()));
        album.setReleaseFormat(StringUtils.trim(source.getPublishFormat()));
        album.setReleasePrice(source.getRelease() == null || source.getRelease().getPrice() == null
                ? "0" : source.getRelease().getPrice());
        album.setReprint(album.getReprint());
        album.setReprints(getVGMdbAlbumReprintList(source.getReprints()));
        album.setRepresentedProducts(VGMdbProductModifier.transformProductList(source.getProducts()));
        album.setStores(_transformVGMDBStoreList(source.getStores()));
        VGMdbCommonModifier.addVGMdbMetaData(album, source.getMeta());
        return album;
    }

    private static List<Album> getVGMdbAlbumReprintList(List<VGMdbAlbumReprint> source) {
        List<Album> albums = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return albums;
        }

        source.stream().forEach((sourceAlbum) -> {
            if (!StringUtils.isEmpty(sourceAlbum.getLink())) {
                Album album = new Album();
                album.setName(StringUtils.trim(sourceAlbum.getName()));
                album.setAlbumCatalog(StringUtils.trim(sourceAlbum.getCatalog()));
                VGMdbCommonModifier.addVGMdbID(album, sourceAlbum.getLink(), DataType.ALBUM);
                albums.add(album);
            }
        });

        return albums;
    }

    private static List<AlbumDisc> getModifiedAlbumDisc(List<VGMdbAlbumDisc> source, Album album) {
        List<AlbumDisc> discs = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return discs;
        }

        source.stream().forEach((sourceDisc) -> {
            AlbumDisc disc = new AlbumDisc();
            disc.setName(StringUtils.trim(sourceDisc.getName()));
            disc.setAlbum(album);
            disc.setDiscLengh(StringUtils.trim(sourceDisc.getDiscLength()));
            disc.setTracks(getModifiedAlbumTrackList(sourceDisc.getTracks(), disc));
            discs.add(disc);
        });

        return discs;
    }

    private static List<AlbumTrack> getModifiedAlbumTrackList(List<VGMdbAlbumTrack> source, AlbumDisc albumDisc) {
        List<AlbumTrack> tracks = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return tracks;
        }

        source.stream().forEach((sourceTrack) -> {
            AlbumTrack track = new AlbumTrack();
            track.setNames(VGMdbCommonModifier.getModifiedNames(sourceTrack.getNames()));
            track.setTrackLength(StringUtils.trim(sourceTrack.getTrackLength()));
            track.setTrackPosition(source.indexOf(sourceTrack) + 1);
            tracks.add(track);

        });

        return tracks;
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
}
