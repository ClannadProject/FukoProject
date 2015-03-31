package de.knoobie.project.fuko.database.utils;

import de.knoobie.project.clannadutils.common.ListUtils;
import de.knoobie.project.clannadutils.common.StringUtils;
import de.knoobie.project.fuko.database.bo.enums.DataType;
import de.knoobie.project.fuko.database.domain.Album;
import de.knoobie.project.fuko.database.domain.AlbumDisc;
import de.knoobie.project.fuko.database.domain.AlbumTrack;
import de.knoobie.project.fuko.database.domain.embeddable.Link;
import de.knoobie.project.fuko.database.domain.embeddable.AlbumLink;
import de.knoobie.project.fuko.database.domain.embeddable.OrganizationLink;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbAlbum;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbAlbumDisc;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbAlbumReprint;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbAlbumTrack;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbEventRelease;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbName;
import de.knoobie.project.nagisa.gson.model.bo.enums.VGMdbNameLanguage;
import java.util.ArrayList;
import java.util.List;

public class VGMdbAlbumModifier {

        public static List<AlbumLink> getAlbumLinks(List<VGMdbAlbum> source) {
        List<AlbumLink> links = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return links;
        }

        source.stream().forEach((album) -> {
            links.add(getAlbumLink(album));
        });

        return links;
    }

    public static AlbumLink getAlbumLink(VGMdbAlbum source) {
        if (source == null) {
            return null;
        }

        List<VGMdbName> names = new ArrayList<>();
        if (!StringUtils.isEmpty(source.getName())) {
            names.add(new VGMdbName(source.getName(), VGMdbNameLanguage.eng));
        }
        if (!ListUtils.isEmpty(source.getNames())) {
            names.addAll(source.getNames());
        }
        AlbumLink link = new AlbumLink();
        link.setAlbumCatalog(StringUtils.trim(source.getCatalog()));
        link.setReleaseDate(StringUtils.trim(source.getReleaseDate()));
        VGMdbCommonModifier.updateLink(link, names, source.getLink());
        return link;
    }

    
    public static List<AlbumLink> getAlbumReprintLinks(List<VGMdbAlbumReprint> source) {
        List<AlbumLink> links = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return links;
        }

        source.stream().forEach((album) -> {
            links.add(getAlbumReprintLink(album));
        });

        return links;
    }

    public static AlbumLink getAlbumReprintLink(VGMdbAlbumReprint source) {
        if (source == null) {
            return null;
        }

        List<VGMdbName> names = new ArrayList<>();
        if (!StringUtils.isEmpty(source.getName())) {
            names.add(new VGMdbName(source.getName(), VGMdbNameLanguage.eng));
        }
        AlbumLink link = new AlbumLink();
        link.setAlbumCatalog(StringUtils.trim(source.getCatalog()));
        VGMdbCommonModifier.updateLink(link, names, source.getLink());
        return link;
    }

 
    
    
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
        OrganizationLink publisher = VGMdbPersonModifier.getOrganizationLink(source.getPublisher());
        if (publisher != null) {
            album.getPublisher().add(publisher);
        }

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
        OrganizationLink publisher = VGMdbOrganizationModifier.getLink(source.getPublisher());
        if (publisher != null) {
            album.getPublisher().add(publisher);
        }
        VGMdbCommonModifier.addVGMdbID(album, source.getLink(), DataType.ALBUM);

        if (incompleteSource) {
            return album;
        }

        album.setArrangers(VGMdbPersonModifier.getArtistLinks(source.getArrangers()));
        album.setComposers(VGMdbPersonModifier.getArtistLinks(source.getComposers()));
        album.setLyricists(VGMdbPersonModifier.getArtistLinks(source.getLyricists()));
        album.setPerformers(VGMdbPersonModifier.getArtistLinks(source.getPerformers()));
        album.setReleaseEvents(VGMdbEventModifier.getLinks(source.getEvents()));
        album.setVgmdbLink(StringUtils.trim(source.getVgmdbLink()));

        album.setPictures(VGMdbCommonModifier.getModifiedPictures(source.getPictures()));

        OrganizationLink distributor = VGMdbOrganizationModifier.getLink(source.getDistributor());
        if (distributor != null) {
            album.getDistributors().add(distributor);
        }

        album.setDiscs(getModifiedAlbumDisc(source.getDiscs(), album));

        album.setDescription(StringUtils.trim(source.getDescription()));
        album.setMediaFormat(StringUtils.trim(source.getMediaFormat()));

        album.setPlatforms(ListUtils.getListAsString(source.getPlatforms()));
        album.setRelatedAlbums(getAlbumLinks(source.getRelatedAlbums()));
        album.setReleaseCurrency(source.getRelease() == null ? StringUtils.EMPTY
                : StringUtils.trim(source.getRelease().getCurrency()));
        album.setReleaseFormat(StringUtils.trim(source.getPublishFormat()));
        album.setReleasePrice(source.getRelease() == null || source.getRelease().getPrice() == null
                ? "0" : source.getRelease().getPrice());
        album.setReprint(album.getReprint());
        album.setReprints(getAlbumReprintLinks(source.getReprints()));
        album.setRepresentedProducts(VGMdbProductModifier.getLinks(source.getProducts()));
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
}
