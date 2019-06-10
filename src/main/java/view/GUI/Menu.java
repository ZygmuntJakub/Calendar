package view.GUI;

import services.CsvService;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Menu aplikacji
 */
public class Menu extends JMenuBar {
	private static final long serialVersionUID = -1458868209363457859L;
	
	JMenu menu, submenu;
    JMenuItem authors;
    JMenuItem save;
    JMenuItem csv;
    JMenuItem settings;
    JFrame settingsFrame;

    public Menu() {

        menu = new JMenu("Kalendarz");
        menu.setMnemonic(KeyEvent.VK_F1);
        menu.getAccessibleContext().setAccessibleDescription(
                "Główne menu kalendarza");
        add(menu);

        setUpAuthors();
        setUpSave();
        exportToCsv();
        setUpSettings();

        menu.add(save);
        menu.add(authors);
        menu.add(csv);
        menu.add(settings);
    }

    private void setUpAuthors(){
        authors = new JMenuItem("O programie");
        authors.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        authors.getAccessibleContext().setAccessibleDescription(
                "Informacje na temat autorów");
        authors.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ImageIcon icon = new ImageIcon("icon.png");
                JOptionPane.showMessageDialog(null, "Program wykonany na zajęcia\nProgramowanie Komponentowe\nAutorzy: \nJakub Nozderka\nJakub Zygmunt",
                        "O programie", JOptionPane.INFORMATION_MESSAGE, icon);
            }
        });
    }

    private void setUpSave(){
        save = new JMenuItem("Zapisz/Odczytaj");
        save.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
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
    private void setUpSettings(){
        settings = new JMenuItem("Ustawienia");
        settings.setAccelerator(KeyStroke.getKeyStroke('M', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        settings.getAccessibleContext().setAccessibleDescription(
                "ZUstawienia aplikacji");
        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame panel = new JFrame();
                JColorChooser colorChooser = new JColorChooser();
                colorChooser.getSelectionModel().addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        Color newColor = colorChooser.getColor();
                        MainWindow.calendar.setColor(newColor);
                        MainWindow.calendar.upDateEventsOnCalendar();
                    }
                });
                panel.add(colorChooser);
                SwingConsole.run(panel, 300,300);
            }
        });
    }
    private void exportToCsv(){
        csv = new JMenuItem("Eksportuj do CSV");
        csv.setAccelerator(KeyStroke.getKeyStroke('E', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        csv.getAccessibleContext().setAccessibleDescription(
                "Eksportuj do CSV");
        csv.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                CsvService.exportToCsv();
            }
        });
    }

}
