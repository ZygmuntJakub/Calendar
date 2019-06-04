package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;

/**
 * Klasa reprezentująca wydarzenie w kalendarzu
 */
@XmlRootElement(name = "event")
@XmlAccessorType(XmlAccessType.FIELD)
public class Event implements Serializable {

    /**
     * Tytuł wydarzenia
     */
    private String title;
    /**
     * Opis wydarzenia
     */
    private String description;
    /**
     * Data i godzina rozpoczęcia wydarzenia
     */
    private Calendar eventDate;
    /**
     * Reprezentuje ile minut przed wydarzenie powinien pojawił się alert
     */
    private Integer duration;
    /**
     * Miejsce wydarzenia
     */
    private String place;

    public Event() {
    }

    /**
     * Konstruktor klasy inicjalizujący wszystkie dostępne pola
     * @param title Tytuł wydarzenia
     * @param description Opis wydarzenia
     * @param date Data i godzina rozpoczęcia wydarzenia
     * @param duration Reprezentuje ile minut przed wydarzenie powinien pojawił się alert
     * @param place Miejsce wydarzenia
     */
    public Event(String title, String description, Calendar date, Integer duration, String place) {
        this.setTitle(title);
        this.setDescription(description);
        this.eventDate = date;
        this.place = place;
        this.duration = duration;
    }

    /**
     * Getter - pobiera nazwę wydarzenia
     * @return Nazwa wydarzenia
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter - ustawia nazwę wydarzenia
     * @param title Tytuł wydarzenia
     */
    public void setTitle(String title) {
        try {
            if (title == null) throw new NullPointerException();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        this.title = title;
    }

    /**
     * Getter - pobiera opis wydarzenia
     * @return Opis wydarzenia
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter - ustawia opis wydarzenia
     * @param description Opis wydarzenia
     */
    public void setDescription(String description) {
        try {
            if (description == null) throw new NullPointerException();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        this.description = description;
    }

    /**
     * Getter - pobiera datę i godzinę wydarzenia
     * @return Data i godzina rozpoczęcia wydarzenia
     */
    public Calendar getDate() {
        return eventDate;
    }

    /**
     * Setter - ustawia datę i godzinę rozpoczęcia wydarzenia
     * @param date Data i godzina wydarzenia
     */
    public void setDate(Calendar date) {
        try {
            if (date == null) throw new NullPointerException();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        this.eventDate = date;
    }

    /**
     * Getter - pobiera miejsce wydarzenia
     * @return Miejsce wydarzenia
     */
    public String getPlace() {
        return place;
    }

    /**
     * Setter - ustawia miejsce wydarzenia
     * @param place miejsce wydarzenia
     */
    public void setPlace(String place) {
        this.place = place;
    }
    /**
     * Getter - pobiera ile minut przed wydarzeniem wyświetlić alert
     * @return  Ile minut przed wydarzenie powinien pojawił się alert
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * Setter - ustawia ile minut przed wydarzenie powinien pojawił się alert
     * @param duration ile minut przed wydarzenie powinien pojawił się alert
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * Odpowiada za sformatowanie informacji o klasie
     * @return Informacje o polach klasy
     */
    @Override
    public String toString() {
		return "Tytuł: " + title + ", data: "
				+ eventDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("pl", "PL")) + ", "
				+ eventDate.get(Calendar.DAY_OF_MONTH) + " "
				+ eventDate.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("pl", "PL")) + " "
				+ eventDate.get(Calendar.YEAR) + " godz. " + eventDate.get(Calendar.HOUR_OF_DAY) + ":"
				+ eventDate.get(Calendar.MINUTE) + ", czas trwania: " + duration + "min., opis: "
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



}
