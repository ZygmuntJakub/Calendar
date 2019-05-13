package view;

import com.toedter.calendar.JCalendar;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class SwingCalendar extends JPanel {

    private static JCalendar calendar = new JCalendar();

    SwingCalendar(){
        calendar.addPropertyChangeListener("calendar", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                final Calendar c = (Calendar) evt.getNewValue();
                System.out.println(c.getTime());
            }
        });
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
