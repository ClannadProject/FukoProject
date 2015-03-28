package de.knoobie.project.fuko.database.service;

import de.knoobie.project.fuko.database.domain.Organization;
import de.knoobie.project.fuko.database.domain.OrganizationRelease;
import de.knoobie.project.fuko.database.domain.Search;
import java.io.Serializable;

public class DBServiceOrganization extends AbstractDBService<Organization> implements Serializable {

    DBServiceOrganization(final FukoDB database) {
        super(database);
    }

    @Override
    public Organization findBy(int vgmdbID) {
        return findBy(Organization.class, vgmdbID);
    }

    @Override
    protected Organization updateDatabaseRelations(Organization org) {
        org.getReleases().replaceAll(this::getORaddRelease);
        return org;
    }

    public OrganizationRelease getORaddRelease(OrganizationRelease arg) {

        if (arg.getAlbum() != null) {
            arg.setAlbum(database.getAlbumService().getORadd(arg.getAlbum()));
        }

        if (arg.getReleaseEvent() != null) {
            arg.setReleaseEvent(database.getEventService().getORadd(arg.getReleaseEvent()));
        }
        return arg;
    }

    @Override
    public Search updateSearch(Search arg) {
        arg.getOrganizations().replaceAll(this::getORadd);
        return arg;
    }

}
