package controller;

import model.EmptyListException;
import model.Event;
import model.NoEventException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EventController implements RepoController<Event, Calendar> {
	List<Event> list;

	public EventController() {
		list = new ArrayList<Event>();
	}

	public void add(Event event) {
		try {
			if (event == null) throw new NullPointerException();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		this.list.add(event);
	}

	public void delete(Event event) {
		try {
			if (!list.contains(event)) throw new NoEventException();
		} catch (NoEventException e) {
			e.printStackTrace();
		}
		this.list.remove(event);
	}

	public void modifyByIndex(int index, Event event) {
		try {
			if (index >= list.size()) throw new IndexOutOfBoundsException();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		list.set(index, event);
	}

	public Event getEvent(int index) {
		try {
			if (index >= list.size()) throw new IndexOutOfBoundsException();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		return list.get(index);
	}

	public List<Event> getAll() {
		try {
			if (list.isEmpty()) throw new EmptyListException();
		} catch (EmptyListException e) {
			e.printStackTrace();
		}
		return list;
	}

}
