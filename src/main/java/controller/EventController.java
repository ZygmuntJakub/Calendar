package controller;

import model.EmptyListException;
import model.Event;
import model.NoEventException;
import view.GUI.EventEditorWindow;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Klasa dziedzicząca po RepoController, stanowiąca kontener na obiekty typu
 * Event.
 *
 */
public class EventController implements RepoController<Event> {

	public EventController() {
		mock();
	}

	/**
	 * {@inheritDoc}
	 */
	public void add(Event event) {
		try {
			if (event == null)
				throw new NullPointerException();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		EventController.list.getEvents().add(event);
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(Event eventToDelete) {
		try {
			if (!list.getEvents().contains(eventToDelete))
				throw new NoEventException();
		} catch (NoEventException e) {
			e.printStackTrace();
		}
		EventController.list.getEvents().remove(eventToDelete);
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteEventByDateAndTitle(Calendar calendar, String title) {
		Calendar c;
		Event e;
		for (int i = 0; i < list.getEvents().size(); i++) {
			e = list.getEvents().get(i);
			c = e.getDate();
			if (c.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
					&& c.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
					&& c.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)
					&& e.getTitle().equals(title)) {
				list.getEvents().remove(e);
			}
		}
	}


	/**
	 * {@inheritDoc}
	 */
	public List<Event> getAll() throws EmptyListException {
			if (list.getEvents().isEmpty())
				throw new EmptyListException();
		return list.getEvents();
	}

	/**
	 * {@inheritDoc}
	 */
	public Event getAlertedEvents() {
		Collections.sort(list.getEvents());
		long minutes;
		int compare;
		for (Event e : list.getEvents()) {
			minutes = getDateDiff(Calendar.getInstance().getTime(), e.getDate().getTime(), TimeUnit.MINUTES) + 1;
			compare = e.getDate().compareTo(Calendar.getInstance());
			if (minutes >= 0 && compare != -1 && minutes <= EventEditorWindow.MINUTES && minutes <= e.getDuration()
					&& e.getDuration() <= EventEditorWindow.MINUTES && e.getDuration() >= 0) {
				e.setDuration((int) minutes);
				return e;
			}
		}
		return null;
	}

    /**
     * Usuwanie wydarzeń starszych niż podana data
     * @param calendar data, przed którą zostaną usunięte wszystkie wydarzenia
     */
    public void deleteEventsOlderThan(Calendar calendar){
		try {
			for(int i = getAll().size()-1 ; i >= 0; i--){
				if(getAll().get(i).getDate().compareTo(calendar) == -1){
					deleteEventByDateAndTitle(getAll().get(i).getDate(), getAll().get(i).getTitle());
				}
			}
		} catch (EmptyListException e) {
			e.printStackTrace();
		}
	}
	/**
	 * {@inheritDoc}
	 */
	public List<String> getAllTitles() {
		List<String> titles = new ArrayList<String>();
		list.getEvents().forEach(x -> titles.add(x.getTitle()));
		return titles;
	}

	/**
	 * Zwraca różnicę czasową dwóch momentów w czasie.
	 * 
	 * @param date1    Pierwsza data.
	 * @param date2    Druga data.
	 * @param timeUnit Jednostka czasu.
	 * @return Różnica w czasie.
	 */
	private long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<String> getDateTitles(Calendar calendar) {
		List<String> titles = new ArrayList<String>();
		Calendar c;
		for (Event e : list.getEvents()) {
			c = e.getDate();
			if (c.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
					&& c.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
					&& c.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)) {
				titles.add(e.getTitle());
			}
		}
		return titles;
	}

	/**
	 * {@inheritDoc}
	 */
	public Event getEventByDateAndTime(Calendar calendar, String title) {
		Calendar c;
		Event e;
		for (int i = 0; i < list.getEvents().size(); i++) {
			e = list.getEvents().get(i);
			c = e.getDate();
			if (c.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
					&& c.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
					&& c.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)
					&& e.getTitle().equals(title)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<String> getDays(Calendar calendar) {
		List<String> days = new ArrayList<>();
		Calendar c;
		for (Event e : list.getEvents()) {
			c = e.getDate();
			if (c.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
					&& c.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)) {
				days.add(String.valueOf(c.get(Calendar.DAY_OF_MONTH)));
			}
		}
		return days;
	}

	/**
	 * {@inheritDoc}
	 */
	public Event getEvent(int index) {
		try {
			if (index >= list.getEvents().size())
				throw new IndexOutOfBoundsException();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		return list.getEvents().get(index);
	}

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * Dodaje przykładowe wydarzenia do kalendarza.
	 */
	private void mock() {
		Event one = new Event("Spotkanie", "Bardzo ważne", Calendar.getInstance(), 12, "LODEX");
		Event two = new Event("Wizyta", "Dentysta", new GregorianCalendar(2019, 5, 3, 1, 12, 10), 230, "gabinet");
		Event three = new Event("Konferencja", "Targi", new GregorianCalendar(2019, 5, 4, 1, 12, 10), 200, "Hala A");
		list.getEvents().add(one);
		list.getEvents().add(two);
		list.getEvents().add(three);
	}

	/**
	 * {@inheritDoc}
	 */
	public void modifyByIndex(int indexOfEvent, Event event) {
		try {
			if (indexOfEvent >= list.getEvents().size())
				throw new IndexOutOfBoundsException();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		list.getEvents().set(indexOfEvent, event);
	}

	/**
	 * {@inheritDoc}
	 */
	public void replaceEvents(Events events) {
		list.setEvents(events.getEvents());
	}

	/**
	 * {@inheritDoc}
	 */
	public void replaceEventValues(Event oldEvent, Event newEvent) {
		oldEvent.setTitle(newEvent.getTitle());
		oldEvent.setDescription(newEvent.getDescription());
		oldEvent.setDate(newEvent.getDate());
		oldEvent.setDuration(newEvent.getDuration());
		oldEvent.setPlace(newEvent.getPlace());
	}

	/**
	 * Wypisuje wydarzenia z kontenera
	 * 
	 * @return sformatowane informacje o wszystkich wydarzeniach
	 */
	@Override
	public String toString() {
		String string = new String();
		for (int i = 0; i < list.getEvents().size(); i++) {
			string += (i + 1) + ". " + this.getEvent(i).toString();
		}
		return string;
	}

}
