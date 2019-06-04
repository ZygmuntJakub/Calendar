package view.GUI;

import com.github.lgooddatepicker.components.TimePicker;
import controller.EventController;
import model.Event;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class EventEditorWindow extends JFrame implements ListSelectionListener, ActionListener {


    public static final EventController repoController = new EventController();

    private JButton save;
    private JButton newEvent;
    private JButton deleteEvent;
    private JComponent editComponent;
    private JTextField title;
    private JTextField place;
    private JTextArea desc;
    private TimePicker timePicker;
    private JSlider minutesSlider;
    private Calendar calendar;


    private JList list;
    private JSplitPane splitPane;

    EventEditorWindow(Calendar calendar) {

        this.calendar = calendar;

        setTitle("Modyfikacja wydarzeń dla " + calendar.getTime());
        list = new JList(repoController.getDateTitles(calendar).toArray());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(this);

        JScrollPane listScrollPane = new JScrollPane(list);


        editComponent = new JPanel();
        editComponent.setLayout(new GridLayout(7, 1));
        save = new JButton("Zapisz");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.isSelectionEmpty()) {
                    return;
                }
                Event oldEvent = repoController.getDateAndTitleEvent(calendar, list.getSelectedValue().toString());
                Event newEvent = getEventFromPanel();
                repoController.replaceEventValues(oldEvent, newEvent);
                resetView();
                list.setListData(repoController.getDateTitles(calendar).toArray());
            }
        });
        newEvent = new JButton("Nowe wydarzenie");
        newEvent.addActionListener(this);
        deleteEvent = new JButton("Usuń wydarzenie");
        deleteEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.isSelectionEmpty()) {
                    return;
                }
                repoController.deleteEventByDateAndTitle(calendar, list.getSelectedValue().toString());
                resetView();
                list.setListData(repoController.getDateTitles(calendar).toArray());
            }
        });

        title = new JTextField("", 15);
        desc = new JTextArea("", 15, 10);
        place = new JTextField("", 15);
        timePicker = new TimePicker();

        minutesSlider = new JSlider(JSlider.HORIZONTAL, 0, 300, 30);
        minutesSlider.setMajorTickSpacing(50);
        minutesSlider.setMinorTickSpacing(20);
        minutesSlider.setPaintTicks(true);
        minutesSlider.setPaintLabels(true);
        JLabel alertLabel = new JLabel("Ustaw alert 30 minut przed wydarzeniem:");
        minutesSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                alertLabel.setText("Ustaw alert " + source.getValue() + " minut przed wydarzeniem:");
            }
        });


        editComponent.add(new JLabel("Tytuł:"));
        editComponent.add(title);
        editComponent.add(new JLabel("Opis:"));
        editComponent.add(desc);
        editComponent.add(new JLabel("Godzina:"));
        editComponent.add(timePicker);
        editComponent.add(new JLabel("Miejsce:"));
        editComponent.add(place);
        editComponent.add(alertLabel);
        editComponent.add(minutesSlider);
        editComponent.add(save);
        editComponent.add(newEvent);
        editComponent.add(deleteEvent);


        //Create a split pane with the two scroll panes in it.
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                listScrollPane, editComponent);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);

        //Provide minimum sizes for the two components in the split pane.
        Dimension minimumSize = new Dimension(100, 50);
        listScrollPane.setMinimumSize(minimumSize);
        //editPane.setMinimumSize(minimumSize);

        //Provide a preferred size for the split pane.
        splitPane.setPreferredSize(new Dimension(400, 200));

        add(splitPane);

    }

    //Listens to the list
    public void valueChanged(ListSelectionEvent e) {

        JList list = (JList) e.getSource();
        if (list.isSelectionEmpty()) {
            return;
        }
        String title = list.getSelectedValue().toString();
        Event readedEvent = repoController.getDateAndTitleEvent(calendar, title);

        this.title.setText(readedEvent.getTitle());
        this.desc.setText(readedEvent.getDescription());
        int hour = readedEvent.getDate().get(Calendar.HOUR_OF_DAY);
        int minutes = readedEvent.getDate().get(Calendar.MINUTE);
        this.timePicker.setTime(LocalTime.of(hour, minutes));
        this.place.setText(readedEvent.getPlace());
        int duration = (int) readedEvent.getDuration().toMinutes();
        this.minutesSlider.setValue(duration);

    }

    private Event getEventFromPanel() {
        String eventTitle = title.getText();
        String eventDesc = desc.getText();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int hourOfDay = timePicker.getTime().getHour();
        int minute = timePicker.getTime().getMinute();

        Calendar eventDate = new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute);

        Duration eventDuration = Duration.ofMinutes(minutesSlider.getValue());

        String eventPlace = place.getText();

        Event event = new Event(eventTitle, eventDesc, eventDate, eventDuration, eventPlace);
        return event;

    }

    private void resetView() {
        this.title.setText("");
        this.desc.setText("");
        this.place.setText("");
        this.minutesSlider.setValue(30);
        this.timePicker.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repoController.add(getEventFromPanel());
        list.setListData(repoController.getDateTitles(calendar).toArray());

    }


}

