package de.knoobie.project.fuko.database.utils;

import de.knoobie.project.clannadutils.common.ListUtils;
import de.knoobie.project.clannadutils.common.StringUtils;
import de.knoobie.project.fuko.database.bo.enums.DataType;
import de.knoobie.project.fuko.database.domain.Organisation;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbOrganisation;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbPerson;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cKnoobie
 */
public class VGMdbOrganisationModifier {

    public static List<Organisation> transformOrganisationList(List<VGMdbOrganisation> source) {
        List<Organisation> organisations = new ArrayList<>();

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

    public static Organisation transformVGMdbOrganisation(VGMdbOrganisation source, boolean incompleteSource) {
        if (source == null) {
            return null;
        }

        Organisation organisation = new Organisation();
        organisation.setName(StringUtils.trim(source.getName()));
        organisation.setNames(VGMdbCommonModifier.getModifiedNames(source.getAliases()));
        VGMdbCommonModifier.addVGMdbID(organisation, source.getLink(), DataType.ORGANISATION);

        if (incompleteSource) {
            return organisation;
        }
        organisation.setVgmdbLink(StringUtils.trim(source.getVgmdbLink()));

        return organisation;
    }

    public static Organisation transformVGMdbPerson(VGMdbPerson source) {
        if (source == null) {
            return null;
        }

        Organisation organisation = new Organisation();
        organisation.setNames(VGMdbCommonModifier.getModifiedNames(source.getNames()));
        VGMdbCommonModifier.addVGMdbID(organisation, source.getLink(), DataType.ORGANISATION);

        return organisation;
    }
}
