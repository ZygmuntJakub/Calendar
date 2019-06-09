package view.GUI;


import services.DatabaseService;
import services.XmlService;

import java.awt.event.*;
import java.util.Locale;

public class GuiStarter {

    public static final XmlService xmlService = new XmlService();

    public static void starter() {
        Locale.setDefault(new Locale("pl", "PL"));
        MainWindow mainWindow = new MainWindow();
        Menu menu = new Menu();
        mainWindow.setJMenuBar(menu);
        SwingConsole.run(mainWindow, 1100, 350);

    }
}
