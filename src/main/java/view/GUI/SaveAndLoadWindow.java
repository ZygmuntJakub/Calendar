package view.GUI;

import controller.DatabaseController;
import services.XmlService;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class SaveAndLoadWindow extends JFrame implements ActionListener {
    public static final DatabaseController databaseController = new DatabaseController();

    private JButton openButton, saveButton;
    private JFileChooser fc = new JFileChooser();

    SaveAndLoadWindow(){
        super("Zapis i odczyt danych");
        setLocation(30, 30);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        JComponent xmlPanel = xmlPanel("Wybierz opcję");
        JComponent datebasePanel = sqlPanel("Wybierz opcję");




        tabbedPane.add("XML", xmlPanel);
        tabbedPane.add("Baza danych", datebasePanel);
        add(tabbedPane);
    }

    protected JComponent sqlPanel(String text) {
        JButton saveSql = new JButton("Zapisz");
        JButton readSql = new JButton("Otwórz");

        saveSql.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                databaseController.saveToDataBase();
            }
        });

        readSql.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                databaseController.loadAndOverrideDataFromDatabase();
                MainWindow.calendar.upDateEventsOnCalendar(); //aktualizacja dat w kalendarzu
            }
        });
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(3, 1));
        panel.add(filler);

        panel.add(saveSql);
        panel.add(readSql);
        return panel;
    }

    protected JComponent xmlPanel(String text) {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Tylko *.xml", "xml", "XML");
        fc.setFileFilter(filter);
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(3, 1));
        panel.add(filler);


        openButton = new JButton("Otwórz");
        openButton.addActionListener(this);

        saveButton = new JButton("Zapisz");
        saveButton.addActionListener(this);

        panel.add(saveButton);
        panel.add(openButton);



        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(SaveAndLoadWindow.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                try {
                    XmlService.unMarshalingExample(file);
                } catch (JAXBException ex) {
                    ex.printStackTrace();
                }
                MainWindow.calendar.upDateEventsOnCalendar(); //aktualizacja dat w kalendarzu
            }
        } else if (e.getSource() == saveButton) {
            int returnVal = fc.showSaveDialog(SaveAndLoadWindow.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                try {
                    XmlService.marshalingExample(file);
                } catch (JAXBException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
