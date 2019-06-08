package view.TUI;

import controller.*;
import model.*;
import services.XmlService;
import view.GUI.MainWindow;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import javax.xml.bind.JAXBException;

import java.io.File;
import java.sql.SQLException;

/**
 * Klasa do obsługi kalendarza poprzez terminal znakowy.
 */
public class TUI {
	public static RepoController<Event> repoController = new EventController();
	public static final DatabaseController databaseController = new DatabaseController();
	private static Scanner scanner = new Scanner(System.in);

	/**
	 * Dodaje wydarzenie do listy wydarzeń.
	 * 
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

		repoController.add(new Event(title, description, date, minutesOfDuration, place));
	}

	/**
	 * Służy do wprowadzania daty i czasu (opcjonalny).
	 * 
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

	private static void deleteEvent() throws SQLException {
		showAllEvents(false);
		System.out.println("Które wydarzenie chcesz usunąć?");
		repoController.delete(repoController.getEvent((intInput()-1)));
	}

	/**
	 * Wczytuje dane typu int.
	 * 
	 * @return Wczytane dane.
	 */
	private static Integer intInput() {
		int data = scanner.nextInt();
		scanner.nextLine();
		return data;
	}

	private static void load() {
		System.out.println("\nSkąd chcesz wczytać dane?\n	1. Z pliku XML.\n	2. Z bazy danych.");
		int choice = intInput();
		if (choice == 1)
			loadFromXML();
		else if (choice == 2)
			databaseController.loadAndOverrideDataFromDatabaseTUI();
		else
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nNiepoprawna wartość.\n");
	}

	private static void loadFromXML() {
		System.out.println("Podaj nazwę pliku.");
		File file = new File(stringInput() + ".xml");
		try {
			XmlService.unMarshalingExample(file);
		} catch (JAXBException ex) {
			ex.printStackTrace();
		}
		MainWindow.calendar.upDateEventsOnCalendar(); // aktualizacja dat w kalendarzu

	}

	/**
	 * Główna metoda klasy.
	 * 
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
	 * 
	 * @throws SQLException SQLException.
	 */
	private static void menu() throws SQLException {
		boolean isTrue = true;
		while (isTrue) {
			System.out.println(
					"1. Dodaj wydarzenie.\n2. Usuń wydarzenie\n3. Wyświetl wydarzenia w dniu X.\n4. Wyświetl wszystkie wydarzenia.\n5. Zapisz dane.\n6. Wczytaj dane.\n7. Zakończ.");
			int key = intInput();
			switch (key) {
			case 1:
				addEvent();
				break;

			case 2:
				deleteEvent();
				break;

			case 3:
				showEventsOfDay();
				break;

			case 4:
				showAllEvents(true);
				break;

			case 5:
				save();
				break;

			case 6:
				load();
				break;

			case 7:
				isTrue = false;
				break;

			default:
				System.out.println("Nie ma takiej opcji. Wprowadz ponownie");
			}
		}

	}

	/**
	 * Modyfikuje wybrane wydarzenie jeśli istnieje na liście.
	 * 
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
		repoController.replaceEventValues(oldEvent, new Event(title, description, cal, minutesOfDuration, place));
	}

	private static void save() {
		System.out.println("\nGdzie chcesz zapisać dane?\n	1. Do XML.\n	2. Do bazy danych.");
		int choice = intInput();
		if (choice == 1)
			saveToXML();
		else if (choice == 2)
			databaseController.saveToDataBaseTUI();
		else
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nNiepoprawna wartość.\n");
	}

	private static void saveToXML() {
		System.out.println("\nPodaj nazwę pliku");
		File file = new File(stringInput() + ".xml");
		try {
			XmlService.marshalingExample(file);
		} catch (JAXBException ex) {
			ex.printStackTrace();
		}
	}

	private static void showAllEvents(boolean ifModify) {
		System.out.println("Wszystkie wydarzenia:\n" + repoController.toString());
		if (ifModify)
			showModifyOption();
	}

	private static void showEventsOfDay() {
		showEvents(dateInput(false));
		showModifyOption();
	}

	/**
	 * Wyświetla wydarzenia wybranego dnia.
	 * 
	 * @param date Data dnia.
	 */
	private static void showEvents(Calendar date) {
		for (int i = 0; i < repoController.getEventsByDate(date).size(); i++) {
			System.out.println((i + 1) + ". " + repoController.getEventsByDate(date).get(i).toString());
		}
	}

	private static void showModifyOption() {
		System.out.println(
				"Czy chcesz zmodyfikować któreś wydarzenie? Jeśli tak to podaj jego numer. Jeśli nie to wpisz 0.");
		int choice = intInput();
		if (choice == 0)
			return;
		else
			modify(repoController.getAll().get((choice - 1)));
	}

	/**
	 * Wczytuje dane typu String.
	 * 
	 * @return Wczytane dane.
	 */
	private static String stringInput() {
		return scanner.nextLine();
	}

}
