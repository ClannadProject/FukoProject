package de.knoobie.project.fuko.database.utils;

import de.knoobie.project.clannadutils.common.ListUtils;
import de.knoobie.project.clannadutils.common.StringUtils;
import de.knoobie.project.fuko.database.bo.enums.DataType;
import de.knoobie.project.fuko.database.domain.Event;
import de.knoobie.project.fuko.database.domain.Name;
import de.knoobie.project.fuko.database.domain.embeddable.EventLink;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbEvent;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbName;
import de.knoobie.project.nagisa.gson.model.bo.enums.VGMdbNameLanguage;
import java.util.ArrayList;
import java.util.List;

public class VGMdbEventModifier {

    public static List<EventLink> getLinks(List<VGMdbEvent> source) {
        List<EventLink> links = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return links;
        }

        source.stream().forEach((org) -> {
            links.add(getLink(org));
        });

        return links;
    }

    public static EventLink getLink(VGMdbEvent source) {
        if (source == null) {
            return null;
        }
        EventLink link = new EventLink();
        List<VGMdbName> names = new ArrayList<>();
        if (!StringUtils.isEmpty(source.getName())) {
            names.add(new VGMdbName(StringUtils.trim(source.getName()), VGMdbNameLanguage.eng));
        }
        if (!StringUtils.isEmpty(source.getShortname())) {
            link.setShortname(StringUtils.trim(source.getShortname()));
        }
        VGMdbCommonModifier.updateLink(link, names, source.getLink());
        return link;
    }

    public static List<Event> transformEventList(List<VGMdbEvent> source) {
        List<Event> events = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return events;
        }

        source.stream().forEach((sourceEvent) -> {
            if (!StringUtils.isEmpty(sourceEvent.getLink())) {
                events.add(transformVGMdbEvent(sourceEvent, true));
            }
        });

        return events;
    }

    public static Event transformVGMdbEvent(VGMdbEvent source, boolean incompleteSource) {
        if (source == null) {
            return null;
        }

        Event event = new Event();
        event.setName(StringUtils.trim(source.getName()));
        event.setStartDate(StringUtils.trim(source.getStartdate()));
        event.setEndDate(StringUtils.trim(source.getEnddate()));
        event.setDescription(StringUtils.trim(source.getDescription()));
        event.setVgmdbLink(StringUtils.trim(source.getVgmdbLink()));
        event.setReleases(VGMdbAlbumModifier.getEventReleaseLinks(source.getReleases()));
        if (!StringUtils.isEmpty(source.getShortname())) {
            Name name = new Name();
            name.setName(StringUtils.trim(source.getShortname()));
            name.setNameLanguage(VGMdbNameLanguage.alias);
            if (event.getNames() == null) {
                event.setNames(new ArrayList<>());
            }
            event.getNames().add(name);
        }
        VGMdbCommonModifier.addVGMdbID(event, source.getLink(), DataType.EVENT);
        return event;
    }
}
