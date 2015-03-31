package de.knoobie.project.fuko.database.service;

import de.knoobie.project.fuko.database.domain.Organization;

public class DBServiceOrganization extends AbstractDBService<Organization> {

    DBServiceOrganization(final FukoDB database) {
        super(database);
    }

    @Override
    public Organization findBy(int vgmdbID) {
        return findBy(Organization.class, vgmdbID);
    }

    @Override
    protected Organization updateDatabaseRelations(Organization org) {
        return org;
    }
}
