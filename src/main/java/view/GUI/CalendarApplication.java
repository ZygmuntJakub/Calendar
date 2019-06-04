package view.GUI;


import services.DatabaseService;

import java.util.Locale;

public class CalendarApplication {

    private static DatabaseService databaseService;

    public static void main(String[] args) {
        Locale.setDefault(new Locale("pl", "PL"));
        MainWindow mainWindow = new MainWindow();
        Menu menu = new Menu();
        mainWindow.setJMenuBar(menu);
        SwingConsole.run(mainWindow, 800, 600);
    }
}
