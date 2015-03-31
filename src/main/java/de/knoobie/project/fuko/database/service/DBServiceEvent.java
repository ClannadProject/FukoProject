package de.knoobie.project.fuko.database.service;

import de.knoobie.project.fuko.database.domain.Event;

public class DBServiceEvent extends AbstractDBService<Event>{

    DBServiceEvent(final FukoDB database) {
       super(database);
    }

    @Override
    public Event findBy(int vgmdbID) {
        return findBy(Event.class, vgmdbID);
    }


    @Override
    protected Event updateDatabaseRelations(Event event) {
      return event;
    }
}
