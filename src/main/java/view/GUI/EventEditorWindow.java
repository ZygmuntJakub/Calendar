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

/**
 * Odpowiada za okno edycji wskazanego wydarzenia (operacje CRUD)
 */
public class EventEditorWindow extends JFrame implements ListSelectionListener, ActionListener {
	private static final long serialVersionUID = 7117203791214798523L;

	private List<String> eventsTitles;

    /**
     * stała, która definiuje maksymalną ilość minut dla powiadomienia o wydarzeniu
     */
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

    /**
     * Tworzymy listę wydarzeń oraz panel do edycji wskazanego wydarzenia, które umieszczamy w JSplitPane
     * @param calendar data wydarzenia potrzbna do identyfikacji wydarzeń
     */
    EventEditorWindow(Calendar calendar) {
        this.calendar = calendar;
        eventsTitles = ApplicationStarter.repoController.getDateTitles(calendar);
        setTitle("Modyfikacja wydarzeń dla " + calendar.getTime());
        list = new JList(eventsTitles.toArray());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(this);

        JScrollPane listScrollPane = new JScrollPane(list);

        editComponent = new JPanel();
        editComponent.setLayout(new GridLayout(7, 1));
        save = new JButton("Zapisz modyfikację wydarzenia");
        checkSaveButton();
        save.setEnabled(false);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (eventsTitles.size() == 0) {
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
        filter.add(createFilterTextField());


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


        //twoorzenie split pane
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                listScrollPane, editComponent);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);
        Dimension minimumSize = new Dimension(100, 50);
        listScrollPane.setMinimumSize(minimumSize);
        splitPane.setPreferredSize(new Dimension(400, 200));

        add(splitPane);

    }

    /**
     * Handler do listy wydarzeń
     * @param e zdarzenie dla listy
     */
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

    /**
     * Pobiera wartości z okna edycji i zapisuje jako obiekt Event (dodatkowo sprawdza czy wszystkie pola są zainicjalizowane niepustymi wartościami)
     * @return wydarzenie na podstawie danych z okna edycji
     * @throws WrongEventValueException zostanie rzucony jeżeli użytkownik wprowadzi złe dane do formularza
     */
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

    private void filterModel(ListModel model, String filter) {
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


    private JTextField createFilterTextField() {
        final JTextField field = new JTextField(15);
        field.getDocument().addDocumentListener(new DocumentListener(){
            @Override public void insertUpdate(DocumentEvent e) { filter(); }
            @Override public void removeUpdate(DocumentEvent e) { filter(); }
            @Override public void changedUpdate(DocumentEvent e) {}
            private void filter() {
                eventsTitles = ApplicationStarter.repoController.getDateTitles(calendar);
                String filter = field.getText();
                filterModel(list.getModel(), filter);
            }
        });
        return field;
    }
    private void checkSaveButton(){
        Timer timer = new Timer(300, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (eventsTitles.size() == 0 || list.isSelectionEmpty()) {
                    save.setEnabled(false);
                } else {
                    save.setEnabled(true);
                }
            }
        });
        timer.start();
    }

    /**
     * Handler dla przycisku dodawania nowego wydarzenia
     * @param e zdarzenie
     */
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

