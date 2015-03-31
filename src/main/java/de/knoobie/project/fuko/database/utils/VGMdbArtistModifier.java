package de.knoobie.project.fuko.database.utils;

import de.knoobie.project.clannadutils.common.ArrayUtils;
import de.knoobie.project.clannadutils.common.DateUtils;
import de.knoobie.project.clannadutils.common.ListUtils;
import de.knoobie.project.clannadutils.common.StringUtils;
import de.knoobie.project.fuko.database.bo.enums.DataType;
import de.knoobie.project.fuko.database.domain.Artist;
import de.knoobie.project.fuko.database.domain.Name;
import de.knoobie.project.fuko.database.domain.embeddable.ArtistLink;
import de.knoobie.project.fuko.database.domain.embeddable.Link;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbAlbum;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbAlbumReprint;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbArtist;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbName;
import de.knoobie.project.nagisa.gson.model.bo.enums.VGMdbNameLanguage;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class VGMdbArtistModifier {
    
    public static List<ArtistLink> getLinks(List<VGMdbArtist> source) {
        List<ArtistLink> links = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return links;
        }

        source.stream().forEach((artist) -> {
            links.add(getLink(artist));
        });

        return links;
    }
    public static ArtistLink getLink(VGMdbArtist source) {
        if (source == null) {
            return null;
        }

        List<VGMdbName> names = new ArrayList<>();
        if (!StringUtils.isEmpty(source.getName())) {
            names.add(new VGMdbName(source.getName(), VGMdbNameLanguage.eng));
        }
        if (!ListUtils.isEmpty(source.getAliases())) {
            names.addAll(source.getAliases());
        }

        ArtistLink link = new ArtistLink();
        VGMdbCommonModifier.updateLink(link, names, source.getLink());
        return link;
    }

    public static List<Artist> transformArtistList(List<VGMdbArtist> source) {
        List<Artist> artists = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return artists;
        }

        source.stream().forEach((sourceArtist) -> {
            if (!StringUtils.isEmpty(sourceArtist.getLink())) {
                artists.add(transformVGMdbArtist(sourceArtist, true));
            }
        });

        return artists;
    }

    public static Artist transformVGMdbArtist(VGMdbArtist source, boolean incompleteSource) {
        if (source == null) {
            return null;
        }
        Artist artist = new Artist();

        artist.setName(StringUtils.trim(source.getName()));
        artist.setNames(VGMdbCommonModifier.getModifiedNames(source.getAliases()));
        artist.setPicture(VGMdbCommonModifier.getModifiedPicture(source.getPicture()));
        VGMdbCommonModifier.addVGMdbID(artist, source.getLink(), DataType.ARTIST);

        if (source.getType() != null) {
            artist.setArtistType(source.getType());
        }

        if (incompleteSource) {
            return artist;
        }

        artist.setCreditedWorks(ListUtils.getListAsString(source.getCreditedWorks()));
        artist.setVgmdbLink(StringUtils.trim(source.getVgmdbLink()));
        artist.setDescription(StringUtils.trim(source.getDescription()));
        artist.setDiscography(VGMdbCommonModifier.getModifiedArtistsAlbums(artist, source.getDiscography()));
        artist.setFeaturedOn(VGMdbCommonModifier.getModifiedArtistsAlbums(artist, source.getFeaturedOn()));

        if (source.getType() != null) {
            switch (source.getType()) {
                case individual:
                    if (source.getPersonInfo() != null) {
                        artist.setBirthdate(StringUtils.trim(source.getPersonInfo().getBirthdate()));
                        artist.setBirthplace(StringUtils.trim(source.getPersonInfo().getBirthplace()));
                        artist.setBloodtype(StringUtils.trim(source.getPersonInfo().getBloodtype()));
                        artist.setGender(source.getPersonInfo().getGender());
                        artist.setBandMemberOf(VGMdbCommonModifier.getModifiedPersons(source.getPersonInfo().getBandMemberOf()));
                    }
                    break;
                case unit:
                    if (source.getBandInfo() != null) {
                        artist.setFormed(StringUtils.trim(source.getBandInfo().getFormed()));
                        artist.setMember(VGMdbCommonModifier.getModifiedPersons(source.getBandInfo().getMember()));
                        artist.setFormerMember(VGMdbCommonModifier.getModifiedPersons(source.getBandInfo().getFormerMember()));
                    }
                    break;
            }
        }

        if (!ArrayUtils.isEmpty(source.getVariations())) {
            for (String variation : source.getVariations()) {
                Name name = new Name();
                name.setName(StringUtils.trim(variation));
                name.setNameLanguage(VGMdbNameLanguage.variation);
                artist.getNames().add(name);
            }
        }

        VGMdbCommonModifier.addVGMdbMetaData(artist, source.getMeta());
        artist.setWebsites(VGMdbCommonModifier.getModifiedWebsites(source.getWebsites()));

        return artist;
    }
}
