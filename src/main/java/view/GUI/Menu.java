package view.GUI;

import com.opencsv.CSVWriter;
import model.Event;
import view.ApplicationStarter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Menu aplikacji
 */
public class Menu extends JMenuBar {

    JMenu menu, submenu;
    JMenuItem authors;
    JMenuItem save;
    JMenuItem csv;

    public Menu() {

        menu = new JMenu("Kalendarz");
        menu.setMnemonic(KeyEvent.VK_F1);
        menu.getAccessibleContext().setAccessibleDescription(
                "Główne menu kalendarza");
        add(menu);

        setUpAuthors();
        setUpSave();
        exportToCsv();

        menu.add(save);
        menu.add(authors);
        menu.add(csv);
    }

    private void setUpAuthors(){
        authors = new JMenuItem("O programie");
        authors.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        authors.getAccessibleContext().setAccessibleDescription(
                "Informacje na temat autorów");
        authors.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ImageIcon icon = new ImageIcon("icon.png");
                JOptionPane.showMessageDialog(null, "Autorzy: \nJakub Nozderka\nJakub Zygmunt",
                        "Autorzy", JOptionPane.INFORMATION_MESSAGE, icon);
            }
        });
    }

    private void setUpSave(){
        save = new JMenuItem("Zapisz/Odczytaj");
        save.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        save.getAccessibleContext().setAccessibleDescription(
                "Zapisz stan aplikacji");
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SaveAndLoadWindow saveAndLoadWindow = new SaveAndLoadWindow();
                saveAndLoadWindow.setVisible(true);
                SwingConsole.run(saveAndLoadWindow, 300, 300);
            }
        });
    }
    private void exportToCsv(){
        csv = new JMenuItem("Eksportuj do CSV");
        csv.setAccelerator(KeyStroke.getKeyStroke('E', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        csv.getAccessibleContext().setAccessibleDescription(
                "Eksportuj do CSV");
        csv.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                File file = new File("calendar.csv");
                try {
                    FileWriter outputfile = new FileWriter(file);

                    // obiekt filewriter jako parametr CSVWriter
                    CSVWriter writer = new CSVWriter(outputfile);

                    // nagłówki CSV
                    String[] header = { "Subject", "Start Date", "Start Time", "Description", "Location" };
                    writer.writeNext(header);
                    String[] items = null;


                    List<String> data = new ArrayList<>();
                    SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy/MM/dd");
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:MM");
                    for (Event event : ApplicationStarter.repoController.getAll()) {
                        data.add(event.getTitle());
                        data.add(dataFormat.format(event.getDate().getTime()));
                        data.add(timeFormat.format(event.getDate().getTime()));
                        data.add(event.getDescription());
                        data.add(event.getPlace());
                        items = data.toArray(new String[data.size()]);
                        writer.writeNext(items);
                        data.clear();
                    }

                    writer.close();
                }
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

}
