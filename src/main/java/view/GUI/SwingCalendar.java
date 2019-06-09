package view.GUI;

import com.toedter.calendar.JCalendar;
import view.ApplicationStarter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

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

        JComponent yearChooser = calendar.getYearChooser();
        JComponent monthChooser = calendar.getMonthChooser();

        add(yearChooser);
        add(monthChooser);

        add(jPanel);
        openEditorWindow();
    }

    public void changeVisible(boolean visible) {
        if (visible) {
            this.setVisible(true);
        } else {
            this.setVisible(false);
        }
    }
    private void changeButtonColor(JButton btn){
        if(ApplicationStarter.repoController.getDays(c).contains(btn.getText())){
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
