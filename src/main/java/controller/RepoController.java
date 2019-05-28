package controller;

import java.util.Calendar;
import java.util.List;

import model.Event;

public interface RepoController<T, I> {
    void add(T t);
    void delete(T t);
    public void modifyByIndex(int index, T t);
    Event getEvent(int i);
    List<T> getAll();
}
