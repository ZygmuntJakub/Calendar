package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonLanguageListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) { //klasa zewnętrzna
        String choose = e.getActionCommand();

        if(choose.equals("English")){
            SwingCalendar.changeLanguage(Languages.English);
        } else if(choose.equals("Polski")){
            SwingCalendar.changeLanguage(Languages.Polski);
        }
    }
}
