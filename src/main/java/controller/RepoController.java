package controller;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import model.Event;

/**
 * Interfejs, z którego dziedziczy klasa EventController
 *
 * @param <T> Typ danych, na których będzie operować klasa pochodna
 */

public interface RepoController<T> {
	public static Events list = new Events();

	/**
	 * Dodaje wydarzenie do listy.
	 * 
	 * @param t Wydarzenie, które ma zostać dodane na koniec listy.
	 * @throws SQLException SQLException
	 */
	public void add(T t) throws SQLException;


	/**
	 * Usuwa wydarzenie z listy.
	 * 
	 * @param t Wydarzenie, które ma zostać usunięte.
	 */
	public void delete(T t);

	/**
	 * Usuwa wydarzenie jeśli istnieje na liście
	 * 
	 * @param calendar Data wydarzenia do usunięcia.
	 * @param title    Nazwa wydarzenia do usunięcia.
	 */
	public void deleteEventByDateAndTitle(Calendar calendar, String title);

	/**
	 * Zwraca wydarzenie podanego dnia o podanej nazwie. Jeśli wydarzenie nie
	 * istnieje zwraca null.
	 * 
	 * @param calendar Data wydarzenia.
	 * @param title    Nazwa wydarzenia.
	 * @return Wydarzenie jeśli istnieje w przeciwnym wypadku null.
	 */
	public Event getEventByDateAndTime(Calendar calendar, String title);

	/**
	 * Zwraca nazwy wydarzeń wybranego dnia.
	 * 
	 * @param calendar Dzień, którego wydarzenia chcemy poznać.
	 * @return Lista nazw wydarzeń danego dnia.
	 */
	public List<String> getDateTitles(Calendar calendar);

	/**
	 * Zwraca dni miesiąca, w których są jakieś wydarzenia.
	 * 
	 * @param calendar Data wybranego miesiąca.
	 * @return Lista dni.
	 */
	public List<String> getDays(Calendar calendar);

	/**
	 * Zwraca wydarzenie z listy o podanym indeksie.
	 * 
	 * @param index Indeks wydarzenia z listy, które ma zostać zwrócone.
	 * @return Wydarzenie o podanym indeksie.
	 * @throws SQLException SQLException.
	 */
	public Event getEvent(int index) throws SQLException;

	/**
	 * Zwraca listę wydarzeń wybranego dnia.
	 * 
	 * @param date Data dnia, którego wydarzenia mają zostać zwrócone.
	 * @return Lista wydarzeń danego dnia.
	 */
	public List<Event> getEventsByDate(Calendar date);

	/**
	 * Zwraca najbliższe wydarzenie, dla którego jest zaplanowane przypomnienie.
	 * 
	 * @return Wydarzenie, o którym musi zostać wyświetlone przypomienie lub null
	 *         jeśli takiego nie ma.
	 */
	public Event getAlertedEvents();

	/**
	 * Zwraca listę wszystkich wydarzeń.
	 * 
	 * @return Lista wydarzeń.
	 */
	public List<T> getAll();

	/**
	 * Zwraca nazwy wszystkich wydarzeń w liście.
	 * 
	 * @return Lista nazw wszystkich wydarzeń.
	 */
	public List<String> getAllTitles();

	/**
	 * Wstawia nowe wydarzenie w miejscu wydarzenia wskazywanego przed indeks.
	 * 
	 * @param indexOfEvent Indeks w liście wydarzenia do zmodyfikowania.
	 * @param t            Wydarzenie, na które ma zostać zamienione wydarzenie o
	 *                     podanym indeksie.
	 */
	public void modifyByIndex(int indexOfEvent, T t);

	/**
	 * Zamienia całą listę wydarzeń na podaną.
	 * 
	 * @param events Nowa lista wydarzeń.
	 */
	public void replaceEvents(Events events);

	/**
	 * Zamienia wartości pól jednego wydarzenia na wartości pól drugiego.
	 * 
	 * @param oldEvent Wydarzenie, którego wartości mają być zmodyfikowane.
	 * @param newEvent Wydarzenie, którego wartościami pól mają zostać zmodyfikowane
	 *                 wartości pól wydarzenia "oldEvent".
	 */
	public void replaceEventValues(Event oldEvent, Event newEvent);

}
