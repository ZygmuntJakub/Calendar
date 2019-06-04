package controller;

import model.EmptyListException;
import model.Event;
import model.NoEventException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class EventController implements RepoController<Event, Calendar> {

    public static Events list;

    public EventController() {
        list = new Events();
        mock();
    }

    public void add(Event event) {
        try {
            if (event == null) throw new NullPointerException();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        this.list.getEvents().add(event);
    }

    public void replaceEventValues(Event oldEvent, Event newEvent) {
        oldEvent.setTitle(newEvent.getTitle());
        oldEvent.setDescription(newEvent.getDescription());
        oldEvent.setDate(newEvent.getDate());
        oldEvent.setDuration(newEvent.getDuration());
        oldEvent.setPlace(newEvent.getPlace());
    }

    public void delete(Event event) {
        try {
            if (!list.getEvents().contains(event)) throw new NoEventException();
        } catch (NoEventException e) {
            e.printStackTrace();
        }
        this.list.getEvents().remove(event);
    }

    public void modifyByIndex(int index, Event event) {
        try {
            if (index >= list.getEvents().size()) throw new IndexOutOfBoundsException();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        list.getEvents().set(index, event);
    }

    public Event getEvent(int index) {
        try {
            if (index >= list.getEvents().size()) throw new IndexOutOfBoundsException();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return list.getEvents().get(index);
    }

    public List<Event> getAll() {
        try {
            if (list.getEvents().isEmpty()) throw new EmptyListException();
        } catch (EmptyListException e) {
            e.printStackTrace();
        }
        return list.getEvents();
    }

    public List<String> getAllTitles() {
        List<String> titles = new ArrayList<String>();
        list.getEvents().forEach(x -> titles.add(x.getTitle()));
        return titles;
    }

    public List<String> getDateTitles(Calendar calendar) {
        List<String> titles = new ArrayList<String>();
        Calendar c;
        for (Event e : list.getEvents()) {
            c = e.getDate();
            if (c.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) && c.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) && c.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)) {
				titles.add(e.getTitle());
            }
        }
        return titles;
    }
    public Event getDateAndTitleEvent(Calendar calendar, String title) {
        List<Event> events = new ArrayList<Event>();
        Calendar c;
        Event e;
        for(int i = 0 ; i < list.getEvents().size() ; i++){
            e = list.getEvents().get(i);
            c = e.getDate();
            if (c.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) && c.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) && c.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH) && e.getTitle().equals(title)) {
                return e;
            }
        }
        return null;
    }

    public void deleteEventByDateAndTitle(Calendar calendar, String title){
        Calendar c;
        Event e;
        for(int i = 0 ; i < list.getEvents().size() ; i++){
            e = list.getEvents().get(i);
            c = e.getDate();
            if (c.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) && c.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) && c.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH) && e.getTitle().equals(title)) {
                list.getEvents().remove(e);
            }
        }
    }

    private void mock() {
        Event one = new Event("Spotkanie", "Bardzo ważne", Calendar.getInstance(), 12, "LODEX");
        Event two = new Event("Wizyta", "Dentysta", new GregorianCalendar(2019, 5, 3, 1, 12,10), 230, "KAKA");
        Event three = new Event("Wizyta", "Dentysta", new GregorianCalendar(2019, 5, 4, 1, 12,10), 200, "KAKA");
        list.getEvents().add(one);
        list.getEvents().add(two);
        list.getEvents().add(three);
    }
    public void changeListData(List<Event> list){
        this.list.setEvents(new ArrayList<>(list));
    }
    
    public List<Event> getEventsByDate(Calendar date) {
		List<Event> events = new ArrayList<Event>();
		for (int i = 0; i < list.getEvents().size(); i++) {
			Calendar c = list.getEvents().get(i).getDate();
			if (c.get(Calendar.YEAR) == date.get(Calendar.YEAR) && c.get(Calendar.MONTH) == date.get(Calendar.MONTH)
					&& c.get(Calendar.DATE) == date.get(Calendar.DATE)) {
				events.add(list.getEvents().get(i));
			}
		}
		return events;
	}

	public void modifyEvent(Event oldEvent, Event newEvent) {
		for (int i = 0; i < list.getEvents().size(); i++) {
            if (list.getEvents().get(i).equals(oldEvent)) {
                list.getEvents().set(i, newEvent);
            }
        }
	}
    public List<String> getDays(Calendar calendar) {
        List<String> days = new ArrayList<>();
        Calendar c;
        for (Event e : list.getEvents()) {
            c = e.getDate();
            if (c.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) && c.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)) {
                days.add(String.valueOf(c.get(Calendar.DAY_OF_MONTH)));
            }
        }
        return days;
    }

    public void replaceEvents(Events events) {
        list.setEvents(events.getEvents());
    }
}
