package controller;

import model.Event;
import services.DatabaseService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DatabaseController implements RepoController<Event, Integer> {

    DatabaseService databaseService;

    public DatabaseController(){
        databaseService = new DatabaseService();
    }


    @Override
    public void add(Event event) throws SQLException {


    }

    @Override
    public void delete(Event event) {

    }

    @Override
    public void modifyByIndex(int index, Event event) {

    }

    @Override
    public Event getEvent(int i) throws SQLException {
        databaseService.connect();

        ResultSet resultSet = databaseService.executeQuery("SELECT * FROM Events;");
        while(resultSet.next()){
            System.out.println(resultSet.getString("name"));
        }

        databaseService.disconnect();
        return null;
    }

    @Override
    public List<Event> getAll() {
        return null;
    }
}
