package view.GUI;

import controller.DatabaseController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SaveWindow extends JFrame {
    public static final DatabaseController databaseController = new DatabaseController();

    SaveWindow(){
        super("Document #");
        setLocation(30, 30);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        JComponent xmlPanel = makeTextPanel("tu będzie xml");


        JComponent datebasePanel = makeTextPanel("Wybierz opcję:");
        JButton saveSql = new JButton("Zapisz do bazy");
        JButton readSql = new JButton("Odczytaj z bazy");

        saveSql.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                databaseController.saveToDataBase();
            }
        });

        readSql.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                databaseController.loadAndOverrideDataFromDatabase();
            }
        });

        datebasePanel.add(saveSql);
        datebasePanel.add(readSql);

        tabbedPane.add("XML", xmlPanel);
        tabbedPane.add("Baza danych", datebasePanel);
        add(tabbedPane);
    }

    protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(3, 1));
        panel.add(filler);
        return panel;
    }

}
