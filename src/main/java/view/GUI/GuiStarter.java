package view.GUI;


import services.DatabaseService;
import services.XmlService;

import javax.swing.*;
import java.awt.event.*;
import java.util.Locale;

public class GuiStarter {

    public static final XmlService xmlService = new XmlService();

    public static void starter() {
        guiLookSetUp();
        Locale.setDefault(new Locale("pl", "PL"));
        MainWindow mainWindow = new MainWindow();
        Menu menu = new Menu();
        mainWindow.setJMenuBar(menu);
        SwingConsole.run(mainWindow, 1300, 350);

    }
    private static void guiLookSetUp(){
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (Exception e) {
            System.out.println("Look and Feel not set");
        }
    }

}
