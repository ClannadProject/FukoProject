package de.knoobie.project.fuko.database.service;

import de.knoobie.project.fuko.database.domain.Organization;

public class DBServiceOrganization extends AbstractVGMdbService<Organization> {

    DBServiceOrganization(final FukoDB database) {
        super(database);
    }

    @Override
    public Organization findByVGMdbID(int vgmdbID) {
        return findBy(Organization.class, vgmdbID);
    }

    @Override
    protected Organization updateDatabaseRelations(Organization org) {
        return org;
    }
    
        public Organization updateFileSystem(Organization org) {
        Organization dbOrg = findByVGMdbID(org.getVgmdbID());

        if (dbOrg == null) {
            return org;
        }
        dbOrg.setPicture(org.getPicture());
        dbOrg.setFolderLocation(org.getFolderLocation());
        dbOrg.setFolderName(org.getFolderName());
        dbOrg.setLocaleStorage(org.isLocaleStorage());
        
        return (Organization) database.update(dbOrg).getResult();        
    }
}
