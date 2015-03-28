package de.knoobie.project.fuko.database.service;

import de.knoobie.project.fuko.database.domain.Event;
import de.knoobie.project.fuko.database.domain.Search;
import java.io.Serializable;

public class DBServiceEvent extends AbstractDBService<Event> implements Serializable {

    DBServiceEvent(final FukoDB database) {
       super(database);
    }

    @Override
    public Event findBy(int vgmdbID) {
        return findBy(Event.class, vgmdbID);
    }


    @Override
    protected Event updateDatabaseRelations(Event event) {
      event.getReleases().replaceAll(database.getAlbumService()::getORadd);
      return event;
    }
    
    @Override
    public Search updateSearch(Search arg) {
        return arg;
    }
}
