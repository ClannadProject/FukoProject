package de.knoobie.project.fuko.database.domain;

import de.knoobie.project.fuko.database.domain.embeddable.Picture;
import de.knoobie.project.fuko.database.domain.msc.MSCEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clannad_user")
public @Getter
@Setter
class User extends MSCEntity {

    @Basic
    @Column(nullable = false)
    private String username;

    @Basic
    @Column(nullable = false)
    private String password;

    @Embedded
    private Picture avatar;

    @JoinTable(name = "clannad_user_favourite_artists")
    @ManyToMany(targetEntity = Artist.class)
    private List<Artist> favouriteArtists = new ArrayList<>();

    @JoinTable(name = "clannad_user_favourite_albums")
    @ManyToMany(targetEntity = Album.class)
    private List<Album> favouriteAblums = new ArrayList<>();

    @JoinTable(name = "clannad_user_favourite_tracks")
    @ManyToMany(targetEntity = AlbumTrack.class)
    private List<AlbumTrack> favouriteTracks = new ArrayList<>();

    @JoinTable(name = "clannad_user_favourite_products")
    @ManyToMany(targetEntity = Product.class)
    private List<Product> favouriteProducts = new ArrayList<>();

    @JoinTable(name = "clannad_user_playlist")
    @ManyToMany(targetEntity = Playlist.class)
    private List<Playlist> myPlayLists = new ArrayList<>();

}
