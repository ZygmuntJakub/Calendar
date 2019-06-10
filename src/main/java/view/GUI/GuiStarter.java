package view.GUI;


import services.XmlService;
import javax.swing.*;
import java.util.Locale;

/**
 * Uruchamia aplikację w trybie GUI
 */
public class GuiStarter {



    /**
     * Tworzy główne okno aplikacji
     */
    public static void starter() {
        guiLookSetUp();
        Locale.setDefault(new Locale("pl", "PL"));
        MainWindow mainWindow = new MainWindow();
        Menu menu = new Menu();
        mainWindow.setJMenuBar(menu);
        SwingConsole.run(mainWindow, 1250, 400);

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
