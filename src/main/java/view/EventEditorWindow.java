package view;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.Calendar;

public class EventEditorWindow extends JFrame implements ListSelectionListener {


    private JButton save;
    private JButton newEvent;
    JComponent editComponent;
    JTextField title;
    JTextArea desc;
    JDateChooser dateChooser;

    private JList list;
    private JSplitPane splitPane;
    private String[] imageNames = { "Bird", "Cat", "Dog", "Rabbit", "Pig", "dukeWaveRed",
            "kathyCosmo", "lainesTongue", "left", "middle", "right", "stickerface"};

    EventEditorWindow(Calendar calendar){
        setTitle("Modyfikacja wydarzeń dla " + calendar.getTime());
        list = new JList(imageNames);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
    JScrollPane listScrollPane = new JScrollPane(list);


        Container container = new Container();

        editComponent = new JPanel();
        editComponent.setLayout(new GridLayout(5,1));
        save = new JButton("Zapisz");
        newEvent = new JButton("Nowe wydarzenie");
        title = new JTextField("", 15);
        desc = new JTextArea("", 15, 10);
        dateChooser = new JDateChooser();
        dateChooser.setCalendar(calendar);
        editComponent.add(new JLabel("Tytuł:"));
        editComponent.add(title);
        editComponent.add(new JLabel("Opis:"));
        editComponent.add(desc);
        editComponent.add(new JLabel("Data:"));
        editComponent.add(dateChooser);
        editComponent.add(save);
        editComponent.add(newEvent);



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
        JList list = (JList)e.getSource();
    }
}
