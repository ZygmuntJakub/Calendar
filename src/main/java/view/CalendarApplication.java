package view;


import com.sun.tools.javac.Main;

import java.util.Locale;

public class CalendarApplication {

    public static void main(String[] args) {
        Locale.setDefault(new Locale("pl", "PL"));
        MainWindow mainWindow = new MainWindow();
        Menu menu = new Menu();
        mainWindow.setJMenuBar(menu);
        SwingConsole.run(mainWindow, 960, 600);
    }
}
