package controller;

import model.EmptyListException;
import model.Event;
import model.NoEventException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class EventController implements RepoController<Event, Calendar> {
    List<Event> list;

    public EventController() {
        list = new ArrayList<Event>();
        mock();
    }

    public void add(Event event) {
        try {
            if (event == null) throw new NullPointerException();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        this.list.add(event);
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

    public List<String> getAllTitles() {
        List<String> titles = new ArrayList<String>();
        list.forEach(x -> titles.add(x.getTitle()));
        return titles;
    }

    public List<String> getDateTitles(Calendar calendar) {
        List<String> titles = new ArrayList<String>();
        Calendar c;
        for (Event e : list) {
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
        for(int i = 0 ; i < list.size() ; i++){
            e = list.get(i);
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
        for(int i = 0 ; i < list.size() ; i++){
            e = list.get(i);
            c = e.getDate();
            if (c.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) && c.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) && c.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH) && e.getTitle().equals(title)) {
                list.remove(e);
            }
        }
    }

    private void mock() {
        Event one = new Event("Spotkanie", "Bardzo ważne", Calendar.getInstance(), Duration.ofMinutes(12), "LODEX");
        Event two = new Event("Wizyta", "Dentysta", new GregorianCalendar(2019, 5, 3, 1, 12,10), Duration.ofMinutes(230), "KAKA");
        Event three = new Event("Wizyta", "Dentysta", new GregorianCalendar(2019, 5, 4, 1, 12,10), Duration.ofMinutes(230), "KAKA");
        list.add(one);
        list.add(two);
        list.add(three);
    }
    public void changeListData(List<Event> list){
        this.list = list;
    }
    
    public List<Event> getEventsByDate(Calendar date) {
		List<Event> events = new ArrayList<Event>();
		for (int i = 0; i < list.size(); i++) {
			Calendar c = list.get(i).getDate();
			if (c.get(Calendar.YEAR) == date.get(Calendar.YEAR) && c.get(Calendar.MONTH) == date.get(Calendar.MONTH)
					&& c.get(Calendar.DATE) == date.get(Calendar.DATE)) {
				events.add(list.get(i));
			}
		}
		return events;
	}

	public void modifyEvent(Event oldEvent, Event newEvent) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(oldEvent)) {
				list.set(i, newEvent);	
			}
		}
		
	}

}
