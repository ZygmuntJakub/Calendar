package services;

import model.Languages;
import view.SwingCalendar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonLanguageListener implements ActionListener {
    public void actionPerformed(ActionEvent e) { //klasa zewnętrzna
        String choose = e.getActionCommand();

        if(choose.equals("English")){
            SwingCalendar.changeLanguage(Languages.English);
        } else if(choose.equals("Polski")){
            SwingCalendar.changeLanguage(Languages.Polski);
        }
    }
}
