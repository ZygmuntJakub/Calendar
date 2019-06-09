package view.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Menu extends JMenuBar {

    JMenu menu, submenu;
    JMenuItem authors;
    JMenuItem save;

    public Menu() {

        menu = new JMenu("Kalendarz");
        menu.setMnemonic(KeyEvent.VK_F1);
        menu.getAccessibleContext().setAccessibleDescription(
                "Główne menu kalendarza");
        add(menu);

        setUpAuthors();
        setUpSave();

        menu.add(save);
        menu.add(authors);
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
}
