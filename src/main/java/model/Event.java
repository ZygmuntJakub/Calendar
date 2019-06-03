package model;

import java.time.Duration;
import java.util.Calendar;

public class Event {
    private String title;
    private String description;
    private Calendar dateStart;
    private Calendar dateEnd;
    private Duration duration;


    String place;

    public Event(String title, String description, Calendar date, Calendar dateEnd, Duration duration, String place) {
        this.dateEnd = dateEnd;
        this.setTitle(title);
        this.setDescription(description);
        this.dateStart = date;
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
        return dateStart;
    }

    public void setDate(Calendar date) {
        try {
            if (date == null) throw new NullPointerException();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        this.dateStart = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "Event [title=" + title + ", description=" + description + ", date=" + dateStart + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dateStart == null) ? 0 : dateStart.hashCode());
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
        if (dateStart == null) {
            if (other.dateStart != null)
                return false;
        } else if (!dateStart.equals(other.dateStart))
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

    public Calendar getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Calendar dateEnd) {
        this.dateEnd = dateEnd;
    }
}
