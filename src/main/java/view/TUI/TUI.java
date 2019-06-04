package view.TUI;

import controller.*;
import model.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import java.sql.SQLException;
import java.time.Duration;

public class TUI {
	static RepoController<Event, Calendar> repo = new EventController();
	static List<Event> events = new ArrayList<Event>();
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws SQLException {
		System.out.println("Witaj w kalendarzu!");
		menu();

		scanner.close();
	}

	private static void menu() throws SQLException {
		boolean isTrue = true;
		while (isTrue) {
			System.out.println(
					"1. Dodaj wydarzenie.\n2. Wyœwietl wydarzenia w dniu X.\n3. Wyœwietl wszystkie wydarzenia.\n5. Zakoñcz.");
			int key = scanner.nextInt();
			scanner.nextLine();
			switch (key) {
			case 1:
				addEvent();
				break;

			case 2:
				showEvents(dateInput(false));
				System.out.println(
						"Czy chcesz zmodyfikowaæ któreœ wydarzenie? Jeœli tak to podaj jego numer. Jeœli nie to wpisz 0.");
				int choice = scanner.nextInt();
				scanner.nextLine();
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

	private static void modify(Event oldEvent) {
		Calendar cal = new GregorianCalendar();
		String title, description, place;
		long minutesOfDuration;

		System.out.println("Podaj nowy tytu³ wydarzenia:");
		title = stringInput();

		System.out.println("Podaj now¹ datê wydarzenia:");
		cal = dateInput(true);

		System.out.println("Podaj nowy czas trwania wydarzenia:");
		minutesOfDuration = longInput();
		scanner.nextLine();

		System.out.println("Podaj nowy opis wydarzenia:");
		description = stringInput();

		System.out.println("Podaj nowe miejsce wydarzenia:");
		place = stringInput();
		repo.modifyEvent(oldEvent, new Event(title, description, cal, Duration.ofMinutes(minutesOfDuration), place));
	}

	private static void showEvents(Calendar date) {
		events = repo.getEventsByDate(date);
		for (int i = 0; i < events.size(); i++) {
			System.out.println((i + 1) + ". " + events.get(i).toString());
		}
	}

	private static Calendar dateInput(boolean time) {
		Calendar date = new GregorianCalendar();
		int year, month, day, hourOfDay, minute;
		System.out.println("Rok:");
		year = scanner.nextInt();
		System.out.println("Miesi¹c:");
		month = scanner.nextInt() - 1;
		System.out.println("Dzieñ:");
		day = scanner.nextInt();
		if (time) {
			System.out.println("Godzina:");
			hourOfDay = scanner.nextInt();
			System.out.println("Minuta:");
			minute = scanner.nextInt();
			date.set(year, month, day, hourOfDay, minute, 0);
		} else
			date.set(year, month, day);
		scanner.nextLine();
		return date;
	}

	private static String stringInput() {
		return scanner.nextLine();
	}

	private static long longInput() {
		return scanner.nextLong();
	}

	private static void addEvent() throws SQLException {
		String title, description, place;
		long minutesOfDuration;
		Calendar date = new GregorianCalendar();

		System.out.println("Podaj tytu³ wydarzenia:");
		title = stringInput();

		System.out.println("Podaj datê wydarzenia:");
		date = dateInput(true);

		System.out.println("Podaj czas trwania wydarzenia:");
		minutesOfDuration = longInput();
		scanner.nextLine();

		System.out.println("Podaj opis wydarzenia:");
		description = stringInput();

		System.out.println("Podaj miejsce wydarzenia:");
		place = stringInput();

		repo.add(new Event(title, description, date, Duration.ofMinutes(minutesOfDuration), place));
	}
}
