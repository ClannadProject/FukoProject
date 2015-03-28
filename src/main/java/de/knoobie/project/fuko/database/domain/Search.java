package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.fuko.database.utils.VGMdbSearchModifier;
import de.knoobie.project.nagisa.gson.model.bo.VGMdbSearch;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


public @Getter @Setter class Search implements Serializable{
   
    private String query;
    private String link;
    private String vgmdbLink;
    private List<Album> albums = new ArrayList<>();
    private List<Artist> artists = new ArrayList<>();
    private List<Organization> organizations = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    
    public Search(){
        
    }
    
    
    public static Search getFromVGMdb(VGMdbSearch search){
        return VGMdbSearchModifier.getSearch(search);
    }
}
