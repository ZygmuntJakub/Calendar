package view.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Menu extends JMenuBar {

    JMenu menu, submenu;
    JMenuItem authors;
    JMenuItem save;

    public Menu() {

        menu = new JMenu("Kalendarz");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "Główne menu kalendarza");
        add(menu);

        setUpAuthors();
        setUpSave();

        menu.add(authors);
        menu.add(save);
    }

    private void setUpAuthors(){
        authors = new JMenuItem("Autorzy",
                KeyEvent.VK_T);
        authors.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
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
        save = new JMenuItem("Zapisz/Odczytaj",
                KeyEvent.VK_1);
        save.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
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
}
