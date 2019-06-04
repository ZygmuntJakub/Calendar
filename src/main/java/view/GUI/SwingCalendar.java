package view.GUI;

import com.toedter.calendar.JCalendar;
import model.Event;
import model.Languages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class SwingCalendar extends JPanel {

    JPanel jPanel;
    private static JCalendar calendar = new JCalendar();
    private Calendar c;

    SwingCalendar() {
        calendar.getDayChooser().setAlwaysFireDayProperty(false);
        c = Calendar.getInstance();

        changeVisible(false);

        jPanel = calendar.getDayChooser().getDayPanel();

        upDateEventsOnCalendar();

        add(calendar.getYearChooser());
        add(calendar.getMonthChooser());
        add(jPanel);
        openEditorWindow();
    }

    public static void changeLanguage(Languages language) {
        if (language.equals(Languages.Polski)) {
            calendar.setLocale(new Locale("pl", "PL"));
        } else if (language.equals(Languages.English)) {
            calendar.setLocale(new Locale("en", "EN"));
        }

        calendar.updateUI();
    }

    public static void changeVisible(boolean visible) {
        if (visible) {
            calendar.setVisible(true);
        } else {
            calendar.setVisible(false);
        }
    }
    private void changeButtonColor(JButton btn){
        if(EventEditorWindow.repoController.getDays(c).contains(btn.getText())){
            btn.setBackground(Color.RED);
            btn.setOpaque(true);
        }else{
            btn.setBackground(Color.WHITE);
            btn.setOpaque(true);
        }
    }

    public  void upDateEventsOnCalendar(){
        Component compo[] = getComponents(jPanel);
        for (Component comp : compo) {
            if (!(comp instanceof JButton))
                continue;
            JButton btn = (JButton) comp;
            changeButtonColor(btn);
            calendar.addPropertyChangeListener("calendar", new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    c = (Calendar) evt.getNewValue();
                    changeButtonColor(btn);
                }
            });
        }
    }
    public void openEditorWindow(){
        Component compo[] = getComponents(jPanel);
        for (Component comp : compo) {
            if (!(comp instanceof JButton))
                continue;
            JButton btn = (JButton) comp;
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    c = new GregorianCalendar(calendar.getYearChooser().getYear(), calendar.getMonthChooser().getMonth(), Integer.valueOf(btn.getText()));
                    EventEditorWindow eventEditorWindow = new EventEditorWindow(c);
                    SwingConsole.run(eventEditorWindow, 800, 500);
                }
            });
        }
    }

    public Component[] getComponents(JPanel jPanel){
        Component compo[] = jPanel.getComponents();
        return compo;
    }
}
