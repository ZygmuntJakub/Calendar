package controller;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import model.Event;

public interface RepoController<T, I> {
    void add(T t) throws SQLException;
    void delete(T t);
    public void modifyByIndex(int index, T t);
    Event getEvent(int i) throws SQLException;
    List<T> getAll();
}
