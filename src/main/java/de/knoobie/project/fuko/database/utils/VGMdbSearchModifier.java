package de.knoobie.project.fuko.database.utils;

import de.knoobie.project.clannadutils.common.StringUtils;
import de.knoobie.project.fuko.database.domain.Search;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbSearch;

public class VGMdbSearchModifier {

    public static Search getSearch(VGMdbSearch source) {
        if (source == null) {
            return null;
        }

        Search search = new Search();
        search.setQuery(StringUtils.trim(source.getQuery()));
        search.setLink(StringUtils.trim(source.getLink()));
        search.setVgmdbLink(StringUtils.trim(source.getVgmdbLink()));
        search.setAlbums(VGMdbAlbumModifier.transformAlbumList(source.getAlbums()));
        search.setArtists(VGMdbArtistModifier.transformArtistList(source.getArtists()));
        search.setProducts(VGMdbProductModifier.transformProductList(source.getProducts()));
        search.setOrganizations(VGMdbOrganizationModifier.transformOrganisationList(source.getOrgs()));

        return search;
    }
}
