package controller;

import model.Event;

import java.util.Calendar;
import java.util.List;

public class EventController implements RepoController<Event, Calendar> {

    @Override
    public void add(Event event) {

    }

    @Override
    public void remove(Event event) {

    }

    @Override
    public void modifyById(Calendar calendar) {

    }

    @Override
    public Event getById(Calendar calendar) {
        return null;
    }

    @Override
    public List<Event> getAll() {
        return null;
    }
}
