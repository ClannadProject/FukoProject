package de.knoobie.project.fuko.database.utils;

import de.knoobie.project.clannadutils.common.ListUtils;
import de.knoobie.project.clannadutils.common.StringUtils;
import de.knoobie.project.fuko.database.bo.enums.DataType;
import de.knoobie.project.fuko.database.domain.Album;
import de.knoobie.project.fuko.database.domain.Organization;
import de.knoobie.project.fuko.database.domain.OrganizationRelease;
import de.knoobie.project.fuko.database.domain.embeddable.OrganizationLink;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbName;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbOrganisation;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbOrganisationRelease;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbPerson;
import de.knoobie.project.nagisa.gson.model.bo.enums.VGMdbNameLanguage;
import java.util.ArrayList;
import java.util.List;

public class VGMdbOrganizationModifier {

    public static List<OrganizationLink> getLinks(List<VGMdbOrganisation> source) {
        List<OrganizationLink> links = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return links;
        }

        source.stream().forEach((org) -> {
            links.add(getLink(org));
        });

        return links;
    }

    public static OrganizationLink getLink(VGMdbOrganisation source) {
        if (source == null) {
            return null;
        }

        List<VGMdbName> names = new ArrayList<>();
        if (!StringUtils.isEmpty(source.getName())) {
            names.add(new VGMdbName(StringUtils.trim(source.getName()), VGMdbNameLanguage.eng));
        }
        if (!ListUtils.isEmpty(source.getAliases())) {
            names.addAll(source.getAliases());
        }
        OrganizationLink link = new OrganizationLink();
        VGMdbCommonModifier.updateLink(link, names, source.getLink());
        return link;
    }

    public static List<Organization> transformOrganisationList(List<VGMdbOrganisation> source) {
        List<Organization> organisations = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return organisations;
        }

        source.stream().forEach((sourceOrganisation) -> {
            if (!StringUtils.isEmpty(sourceOrganisation.getLink())) {
                organisations.add(transformVGMdbOrganisation(sourceOrganisation, true));
            }
        });

        return organisations;
    }

    public static Organization transformVGMdbOrganisation(VGMdbOrganisation source, boolean incompleteSource) {
        if (source == null) {
            return null;
        }

        if (StringUtils.isEmpty(source.getLink())) {
            return null;
        }

        Organization organisation = new Organization();
        organisation.setName(StringUtils.trim(source.getName()));
        organisation.setNames(VGMdbCommonModifier.getModifiedNames(source.getAliases()));
        organisation.setPicture(VGMdbCommonModifier.getModifiedPicture(source.getPicture()));
        VGMdbCommonModifier.addVGMdbID(organisation, source.getLink(), DataType.ORGANIZATION);

        if (incompleteSource) {
            return organisation;
        }
        organisation.setDescription(StringUtils.trim(source.getDescription()));
        organisation.setVgmdbLink(StringUtils.trim(source.getVgmdbLink()));
        organisation.setVgmdbLink(StringUtils.trim(source.getVgmdbLink()));
        organisation.setReleases(getOrganisationsRelease(source.getReleases(), organisation));
        VGMdbCommonModifier.addVGMdbMetaData(organisation, source.getMeta());

        return organisation;
    }

    public static Organization transformVGMdbPerson(VGMdbPerson source) {
        if (source == null) {
            return null;
        }

        Organization organisation = new Organization();
        organisation.setNames(VGMdbCommonModifier.getModifiedNames(source.getNames()));
        VGMdbCommonModifier.addVGMdbID(organisation, source.getLink(), DataType.ORGANIZATION);

        return organisation;
    }

    public static List<OrganizationRelease> transformVGMdbPersonToRelease(Album album, VGMdbPerson source, String role) {
        List<OrganizationRelease> releases = new ArrayList<>();
        if (source == null) {
            return releases;
        }

        OrganizationRelease release = new OrganizationRelease();
        release.setAlbum(album);
        release.setPublisher(transformVGMdbPerson(source));
        release.setOrganizationRole(StringUtils.trim(role));

        releases.add(release);

        return releases;
    }

    public static List<OrganizationRelease> transformVGMdbOrganisationToRelease(Album album, VGMdbOrganisation source, String role) {
        List<OrganizationRelease> releases = new ArrayList<>();
        if (source == null) {
            return releases;
        }

        OrganizationRelease release = new OrganizationRelease();
        release.setAlbum(album);
        release.setPublisher(transformVGMdbOrganisation(source, true));
        release.setOrganizationRole(StringUtils.trim(role));

        releases.add(release);

        return releases;
    }

    public static List<OrganizationRelease> getOrganisationsRelease(List<VGMdbOrganisationRelease> source, Organization org) {
        List<OrganizationRelease> releases = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return releases;
        }

        source.stream().forEach((sourceRelease) -> {
            if (!StringUtils.isEmpty(sourceRelease.getLink())) {

                OrganizationRelease release = new OrganizationRelease();
                release.setReleaseEvent(VGMdbEventModifier.transformVGMdbEvent(sourceRelease.getEvent(), true));

                Album album = new Album();
                album.setReleaseDate(StringUtils.trim(sourceRelease.getDate()));
                album.setAlbumType(StringUtils.trim(sourceRelease.getType()));
                album.setNames(VGMdbCommonModifier.getModifiedNames(sourceRelease.getNames()));
                album.setAlbumCatalog(StringUtils.trim(sourceRelease.getCatalog()));
                VGMdbCommonModifier.addVGMdbID(album, sourceRelease.getLink(), DataType.ALBUM);

                release.setAlbum(album);
                release.setOrganizationRole(StringUtils.trim(sourceRelease.getRole()));
                release.setPublisher(org);
                releases.add(release);
            }
        });

        return releases;
    }
}
