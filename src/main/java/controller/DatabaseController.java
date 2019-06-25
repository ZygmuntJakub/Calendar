package controller;

import model.Event;
import services.DatabaseService;
import view.ApplicationStarter;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Klasa używana przy zapisie do bazy danych.
 */
public class DatabaseController {
	private DatabaseService databaseService;

	public DatabaseController() {
		databaseService = new DatabaseService();
	}

	/**
	 * Odpowiada za pobranie wydarzeń z bazy danych i nadpisanie obecnego stanu aplikacji tymi danymi
	 */
	public void loadFromDatabeseAndMoveToRepo() {
		ApplicationStarter.repoController.replaceEvents(new Events((ArrayList<Event>) getAllEvents()));
	}

	private List<Event> getAllEvents() throws Error {
		databaseService.connect();

		List<Event> base = new ArrayList<Event>();

		try {
			String query = "SELECT * FROM Events";
			ResultSet rs = databaseService.executeQuery(query);

			while (rs.next()) {
				Timestamp timestamp = rs.getTimestamp(4);
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(timestamp.getTime());
				Event E = new Event(rs.getString(2), rs.getString(3), calendar, Integer.valueOf(rs.getString(5)),
						rs.getString(6));
				base.add(E);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		databaseService.disconnect();

		return base;
	}

	/**
	 * Zapisuje listę wydarzeń do bazy danych.
	 */
	public void saveToDataBase() {
		databaseService.connect();

		List<Event> events = null;
		try {
			events = ApplicationStarter.repoController.getAll();
		} catch (EmptyListException e) {
			e.printStackTrace();
		}
		databaseService.resetDatabase();
		for (Event e : events) {
			databaseService.executePreparedUpdate(e.getTitle(), e.getDescription(), e.getDate(), e.getPlace(), e.getDuration());
		}

		databaseService.disconnect();
	}

}
