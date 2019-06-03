package view.GUI;

import com.toedter.calendar.JCalendar;
import model.Languages;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Locale;

public class SwingCalendar extends JPanel {

    private static JCalendar calendar = new JCalendar();
    private Calendar c;

    SwingCalendar(){
        c = Calendar.getInstance();
        calendar.addPropertyChangeListener("calendar", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                c = (Calendar) evt.getNewValue();
                EventEditorWindow eventEditorWindow = new EventEditorWindow(c);
                SwingConsole.run(eventEditorWindow, 800, 500);

            }
        });

        changeVisible(false);
        add(calendar);
    }

    public static void changeLanguage(Languages language){
        if(language.equals(Languages.Polski)){
            calendar.setLocale(new Locale("pl", "PL"));
        } else if(language.equals(Languages.English)){
            calendar.setLocale(new Locale("en", "EN"));
        }

        calendar.updateUI();
    }

    public static void changeVisible(boolean visible){
        if(visible){
            calendar.setVisible(true);
        } else{
            calendar.setVisible(false);
        }
    }
}
