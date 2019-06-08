package view.TUI;

import controller.*;
import model.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import java.sql.SQLException;

/**
 * Klasa do obsługi kalendarza poprzez terminal znakowy.
 */
public class TUI {
	private static RepoController<Event> repo = new EventController();
	private static List<Event> events = new ArrayList<Event>();
	private static Scanner scanner = new Scanner(System.in);
	
	/**
	 * Dodaje wydarzenie do listy wydarzeń.
	 * @throws SQLException
	 */
	private static void addEvent() throws SQLException {
		String title, description, place;
		Integer minutesOfDuration;
		Calendar date = new GregorianCalendar();

		System.out.println("Podaj tytuł wydarzenia:");
		title = stringInput();

		System.out.println("Podaj datę wydarzenia:");
		date = dateInput(true);

		System.out.println("Podaj czas trwania wydarzenia:");
		minutesOfDuration = intInput();

		System.out.println("Podaj opis wydarzenia:");
		description = stringInput();

		System.out.println("Podaj miejsce wydarzenia:");
		place = stringInput();

		repo.add(new Event(title, description, date, minutesOfDuration, place));
	}

	/**
	 * Służy do wprowadzania daty i czasu (opcjonalny).
	 * @param time Jeśli false to wczytuje tylko datę, jeśli true to także godzinę.
	 * @return Wczytana data.
	 */
	private static Calendar dateInput(boolean time) {
		Calendar date = new GregorianCalendar();
		int year, month, day, hourOfDay, minute;
		System.out.println("Rok:");
		year = intInput();
		System.out.println("Miesi�c:");
		month = intInput() - 1;
		System.out.println("Dzie�:");
		day = intInput();
		if (time) {
			System.out.println("Godzina:");
			hourOfDay = intInput();
			System.out.println("Minuta:");
			minute = intInput();
			date.set(year, month, day, hourOfDay, minute, 0);
		} else
			date.set(year, month, day);
		return date;
	}
	
	/**
	 * Wczytuje dane typu int.
	 * @return Wczytane dane.
	 */
	private static Integer intInput() {
		int data = scanner.nextInt();
		scanner.nextLine();
		return data;
	}

	/**
	 * Główna metoda klasy.
	 * @param args argumenty wywołania.
	 * @throws SQLException SQLException.
	 */
	public static void main(String[] args) throws SQLException {
		System.out.println("Witaj w kalendarzu!");
		menu();

		scanner.close();
	}

	/**
	 * Wyświetla menu programu.
	 * @throws SQLException SQLException.
	 */
	private static void menu() throws SQLException {
		boolean isTrue = true;
		while (isTrue) {
			System.out.println(
					"1. Dodaj wydarzenie.\n2. Wyświetl wydarzenia w dniu X.\n3. Wyświetl wszystkie wydarzenia.\n5. Zakończ.");
			int key = intInput();
			switch (key) {
			case 1:
				addEvent();
				break;

			case 2:
				showEvents(dateInput(false));
				System.out.println(
						"Czy chcesz zmodyfikować któreś wydarzenie? Jeśli tak to podaj jego numer. Jeśli nie to wpisz 0.");
				int choice = intInput();
				if (choice == 0)
					break;
				else
					modify(events.get((choice - 1)));
				break;

			case 3:
				System.out.println(repo.getAll());
				break;

			case 5:
				isTrue = false;
				break;

			default:
				System.out.println("Nie ma takiej opcji. Wprowadz ponownie");
			}
		}

	}

	/**
	 * Modyfikuje wybrane wydarzenie jeśli istnieje na liście.
	 * @param oldEvent Wydarzenie do zmodyfikowania.
	 */
	private static void modify(Event oldEvent) {
		Calendar cal = new GregorianCalendar();
		String title, description, place;
		Integer minutesOfDuration;

		System.out.println("Podaj nowy tytuł wydarzenia:");
		title = stringInput();

		System.out.println("Podaj nową datę wydarzenia:");
		cal = dateInput(true);

		System.out.println("Podaj nowy czas trwania wydarzenia:");
		minutesOfDuration = intInput();

		System.out.println("Podaj nowy opis wydarzenia:");
		description = stringInput();

		System.out.println("Podaj nowe miejsce wydarzenia:");
		place = stringInput();
		repo.replaceEventValues(oldEvent, new Event(title, description, cal, minutesOfDuration, place));
	}

	/**
	 * Wyświetla wydarzenia wybranego dnia.
	 * @param date Data dnia.
	 */
	private static void showEvents(Calendar date) {
		events = repo.getEventsByDate(date);
		for (int i = 0; i < events.size(); i++) {
			System.out.println((i + 1) + ". " + events.get(i).toString());
		}
	}

	/**
	 * Wczytuje dane typu String.
	 * @return Wczytane dane.
	 */
	private static String stringInput() {
		return scanner.nextLine();
	}

}
