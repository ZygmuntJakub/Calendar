package model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class Event {
	String title;
	String description;
	Calendar date;

	public Event(String title, String description, Calendar date) {
		this.setTitle(title);
		this.setDescription(description);
		this.date = new GregorianCalendar(TimeZone.getDefault(), new Locale("pl", "PL"));
		this.setDate(date);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		try {
			if (title == null) throw new NullPointerException();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		try {
		if (description == null) throw new NullPointerException();
	} catch (NullPointerException e) {
		e.printStackTrace();
	}
		this.description = description;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		try {
			if (date == null) throw new NullPointerException();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		this.date = date;
	}

	@Override
	public String toString() {
		return "Event [title=" + title + ", description=" + description + ", date=" + date + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
