package view.GUI;

import com.github.lgooddatepicker.components.TimePicker;
import model.Event;
import model.WrongEventValueException;
import view.ApplicationStarter;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class EventEditorWindow extends JFrame implements ListSelectionListener, ActionListener {

    private List<String> eventsTitles;

    public static final int MINUTES = 300;

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
    private JComponent filter;


    private JList list;
    private JSplitPane splitPane;

    EventEditorWindow(Calendar calendar) {
        eventsTitles = ApplicationStarter.repoController.getDateTitles(calendar);

        this.calendar = calendar;

        setTitle("Modyfikacja wydarzeń dla " + calendar.getTime());
        list = new JList(eventsTitles.toArray());
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
                Event oldEvent = ApplicationStarter.repoController.getEventByDateAndTime(calendar, list.getSelectedValue().toString());
                Event newEvent = null;
                try {
                    newEvent = getEventFromPanel();
                } catch (WrongEventValueException ex) {
                    JOptionPane.showMessageDialog(null, "Podano złą wartość");
                    ex.printStackTrace();
                }
                ApplicationStarter.repoController.replaceEventValues(oldEvent, newEvent);
                resetView();
                list.setListData(ApplicationStarter.repoController.getDateTitles(calendar).toArray());
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
                ApplicationStarter.repoController.deleteEventByDateAndTitle(calendar, list.getSelectedValue().toString());
                resetView();
                list.setListData(ApplicationStarter.repoController.getDateTitles(calendar).toArray());
                MainWindow.calendar.upDateEventsOnCalendar();
            }
        });

        title = new JTextField("", 15);
        desc = new JTextArea("", 15, 10);
        place = new JTextField("", 15);
        timePicker = new TimePicker();

        minutesSlider = new JSlider(JSlider.HORIZONTAL, 0, EventEditorWindow.MINUTES, 30);
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
        filter = new JPanel();
        filter.add(new JLabel("Filtruj wydarzenia: "));
        filter.add(createTextField());


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
        editComponent.add(filter);


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
        Event readedEvent = ApplicationStarter.repoController.getEventByDateAndTime(calendar, title);

        this.title.setText(readedEvent.getTitle());
        this.desc.setText(readedEvent.getDescription());
        int hour = readedEvent.getDate().get(Calendar.HOUR_OF_DAY);
        int minutes = readedEvent.getDate().get(Calendar.MINUTE);
        this.timePicker.setTime(LocalTime.of(hour, minutes));
        this.place.setText(readedEvent.getPlace());
        int duration = readedEvent.getDuration();
        this.minutesSlider.setValue(duration);

    }

    private Event getEventFromPanel() throws WrongEventValueException {
        Event event = null;
        if (
                title.getText().equals(null) || title.getText().equals("") ||
                        desc.getText().equals(null) || desc.getText().equals("") ||
                        timePicker.getText().equals(null) || timePicker.getText().equals("") ||
                        place.getText().equals(null) || place.getText().equals("")

        ){
            throw new WrongEventValueException("Podano złą wartość");
        }else{
            String eventTitle = title.getText();
            String eventDesc = desc.getText();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            int hourOfDay = timePicker.getTime().getHour();
            int minute = timePicker.getTime().getMinute();
            String eventPlace = place.getText();
            Calendar eventDate = new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute);
            Integer eventDuration = minutesSlider.getValue();
            event = new Event(eventTitle, eventDesc, eventDate, eventDuration, eventPlace);
        }
        return event;

    }

    private void resetView() {
        this.title.setText("");
        this.desc.setText("");
        this.place.setText("");
        this.minutesSlider.setValue(30);
        this.timePicker.setText("");
    }

    public void filterModel(ListModel model, String filter) {
        DefaultListModel<String> eventTitles = new DefaultListModel<>();
        for(int i = 0 ; i < model.getSize() ; i++) eventTitles.add(i, (String) model.getElementAt(i));


        for (String s : eventsTitles) {
            if (!s.startsWith(filter)) {
                if (eventTitles.contains(s)) {
                    eventTitles.removeElement(s);
                }
            } else {
                if (!eventTitles.contains(s)) {
                    eventTitles.addElement(s);
                }
            }
        }
        list.setListData(eventTitles.toArray());
    }

    private JTextField createTextField() {
        final JTextField field = new JTextField(15);
        field.getDocument().addDocumentListener(new DocumentListener(){
            @Override public void insertUpdate(DocumentEvent e) { filter(); }
            @Override public void removeUpdate(DocumentEvent e) { filter(); }
            @Override public void changedUpdate(DocumentEvent e) {}
            private void filter() {
                String filter = field.getText();
                filterModel(list.getModel(), filter);
            }
        });
        return field;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Event event = null;
        try {
                event = getEventFromPanel();
        } catch (WrongEventValueException ex) {
            JOptionPane.showMessageDialog(null, "Podano złą wartość");
            ex.printStackTrace();
        }
        if(ApplicationStarter.repoController.getEventByDateAndTime(event.getDate(), event.getTitle()) != null){
            JOptionPane.showMessageDialog(null, "Wydarzenie o podanej nazwie już istnieje!");
        }else{
            ApplicationStarter.repoController.add(event);
            list.setListData(ApplicationStarter.repoController.getDateTitles(calendar).toArray());
            MainWindow.calendar.upDateEventsOnCalendar();
        }

    }


}

