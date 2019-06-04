package controller;

import model.Event;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "events")
@XmlAccessorType(XmlAccessType.FIELD)
public class Events
{
    @XmlElement(name = "event")
    private ArrayList<Event> events = null;

    Events(){
        this.events = new ArrayList<>();
    }

    public ArrayList<Event> getEvents() {
        return this.events;
    }

    public void setEvents(ArrayList<Event> event) {
        this.events = event;
    }
}