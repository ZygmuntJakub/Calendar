package controller;

import model.Event;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "events")
@XmlAccessorType(XmlAccessType.FIELD)

/**
 * Klasa stanowiąca kontener na wydarzenia używana przy zapisie do formatu XML.
 */
public class Events
{
    @XmlElement(name = "event")
    private ArrayList<Event> events = null;

    Events(){
        this.events = new ArrayList<>();
    }

    /**
     * Zwraca listę wydarzeń.
     * @return Lista wydarzeń.
     */
    public ArrayList<Event> getEvents() {
        return this.events;
    }
    
    /**
     * Ustawia nową listę wydarzeń.
     * @param event Nowa lista wydarzeń.
     */
    public void setEvents(ArrayList<Event> event) {
        this.events = event;
    }
}