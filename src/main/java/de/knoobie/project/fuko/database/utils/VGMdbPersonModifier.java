package de.knoobie.project.fuko.database.utils;

import de.knoobie.project.clannadutils.common.ListUtils;
import de.knoobie.project.fuko.database.domain.embeddable.ArtistLink;
import de.knoobie.project.fuko.database.domain.embeddable.OrganizationLink;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbName;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbPerson;
import java.util.ArrayList;
import java.util.List;

public class VGMdbPersonModifier {

    public static List<OrganizationLink> getOrganizationLinks(List<VGMdbPerson> source) {
        List<OrganizationLink> links = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return links;
        }

        source.stream().forEach((person) -> {
            links.add(getOrganizationLink(person));
        });

        return links;
    }

    public static OrganizationLink getOrganizationLink(VGMdbPerson source) {
        if (source == null) {
            return null;
        }

        List<VGMdbName> names = new ArrayList<>();
        if (!ListUtils.isEmpty(source.getNames())) {
            names.addAll(source.getNames());
        }
        OrganizationLink link = new OrganizationLink();
        VGMdbCommonModifier.updateLink(link, names, source.getLink());
        return link;
    }

    public static List<ArtistLink> getArtistLinks(List<VGMdbPerson> source) {
        List<ArtistLink> links = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return links;
        }

        source.stream().forEach((person) -> {
            links.add(getArtistLink(person));
        });

        return links;
    }

    public static ArtistLink getArtistLink(VGMdbPerson source) {
        if (source == null) {
            return null;
        }

        List<VGMdbName> names = new ArrayList<>();
        if (!ListUtils.isEmpty(source.getNames())) {
            names.addAll(source.getNames());
        }
        ArtistLink link = new ArtistLink();
        VGMdbCommonModifier.updateLink(link, names, source.getLink());
        return link;
    }
}
