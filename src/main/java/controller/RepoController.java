package controller;

import java.util.Calendar;
import java.util.List;

public interface RepoController<T, I> {
    void add(T t);
    void remove(T t);
    void modifyById(I i);
    T getById(I i);
    List<T> getAll();
}
