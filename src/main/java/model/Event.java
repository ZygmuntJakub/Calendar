package model;

import java.time.Duration;
import java.util.Calendar;
import java.util.Locale;

public class Event {
    private String title;
    private String description;
    private Calendar eventDate;
    private Duration duration;
    private String place;

    public Event(String title, String description, Calendar date, Duration duration, String place) {
        this.setTitle(title);
        this.setDescription(description);
        this.eventDate = date;
        this.place = place;
        this.duration = duration;
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
        return eventDate;
    }

    public void setDate(Calendar date) {
        try {
            if (date == null) throw new NullPointerException();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        this.eventDate = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
		return "Tytu³: " + title + ", data: "
				+ eventDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("pl", "PL")) + ", "
				+ eventDate.get(Calendar.DAY_OF_MONTH) + " "
				+ eventDate.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("pl", "PL")) + " "
				+ eventDate.get(Calendar.YEAR) + " godz. " + eventDate.get(Calendar.HOUR_OF_DAY) + ":"
				+ eventDate.get(Calendar.MINUTE) + ", czas trwania: " + duration.toMinutes() + "min., opis: "
				+ description + ", miejsce: " + place + ".";
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((eventDate == null) ? 0 : eventDate.hashCode());
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
        if (eventDate == null) {
            if (other.eventDate != null)
                return false;
        } else if (!eventDate.equals(other.eventDate))
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

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

}
