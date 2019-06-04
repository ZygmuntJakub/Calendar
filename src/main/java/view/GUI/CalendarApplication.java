package view.GUI;


import services.DatabaseService;
import services.XmlService;

import java.util.Locale;

public class CalendarApplication {

    public static final XmlService xmlService = new XmlService();

    public static void main(String[] args) {
        Locale.setDefault(new Locale("pl", "PL"));
        MainWindow mainWindow = new MainWindow();
        Menu menu = new Menu();
        mainWindow.setJMenuBar(menu);
        SwingConsole.run(mainWindow, 800, 600);
    }
}
