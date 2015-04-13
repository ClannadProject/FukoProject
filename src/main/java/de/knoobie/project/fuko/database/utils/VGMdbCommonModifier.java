package de.knoobie.project.fuko.database.utils;

import de.knoobie.project.clannadutils.common.ListUtils;
import de.knoobie.project.clannadutils.common.StringUtils;
import de.knoobie.project.fuko.database.bo.enums.DataType;
import de.knoobie.project.fuko.database.domain.embeddable.Link;
import de.knoobie.project.fuko.database.domain.Name;
import de.knoobie.project.fuko.database.domain.embeddable.Picture;
import de.knoobie.project.fuko.database.domain.embeddable.WebsiteLink;
import de.knoobie.project.fuko.database.domain.msc.MSCVGMdbEntity;
import de.knoobie.project.fuko.database.domain.msc.MSCVGMdbMeta;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbMeta;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbName;
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
                            link.getType().getType() + "/",
                            StringUtils.EMPTY), null, true));
        }
        List<Name> rightNames = getModifiedNames(names);
        if (rightNames.size() >= 1) {
            link.setPrimaryName(rightNames.get(0));
        }
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

    public static List<Picture> getModifiedPictures(List<VGMdbPicture> source) {
        List<Picture> pictures = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return pictures;
        }

        source.stream().forEach((sourcePicture) -> {
            pictures.add(getModifiedPicture(sourcePicture, false));
        });

        return pictures;
    }

    public static Picture getModifiedPicture(VGMdbPicture source, boolean isCover) {
        if (source == null) {
            return null;
        }

        Picture picture = new Picture();
        picture.setPictureName(StringUtils.trim(source.getName()));
        picture.setUrlFull(StringUtils.trim(source.getFull()));
        picture.setUrlSmall(StringUtils.trim(source.getSmall()));
        picture.setUrlThumbnail(StringUtils.trim(source.getThumbnail()));
        picture.setCover(isCover);
        return picture;
    }

    public static List<WebsiteLink> getWebsiteLinks(List<VGMdbWebsite> source) {
        List<WebsiteLink> links = new ArrayList<>();

        if (ListUtils.isEmpty(source)) {
            return links;
        }

        source.stream().forEach((website) -> {
            links.add(getWebsiteLink(website));
        });

        return links;
    }

    public static WebsiteLink getWebsiteLink(VGMdbWebsite source) {
        if (source == null) {
            return null;
        }

        List<VGMdbName> names = new ArrayList<>();
        names.add(new VGMdbName(source.getName(), VGMdbNameLanguage.eng));

        WebsiteLink link = new WebsiteLink();
        link.setWebsiteType(source.getType() == null ? VGMdbWebsiteType.unknown : source.getType());
        link.setLink(StringUtils.trim(source.getLink()));
        
        List<Name> rightNames = getModifiedNames(names);
        if (rightNames.size() >= 1) {
            link.setPrimaryName(rightNames.get(0));
        }
        
        return link;
    }

}
