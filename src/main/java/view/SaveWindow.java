package view;

import javax.swing.*;
import java.awt.*;


public class SaveWindow extends JFrame {

    SaveWindow(){
        super("Document #");
        setLocation(30, 30);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        JComponent xmlPanel = makeTextPanel("tu będzie xml");
        JComponent datebasePanel = makeTextPanel("tu będzie datebase");

        tabbedPane.add("XML", xmlPanel);
        tabbedPane.add("Baza danych", datebasePanel);
        add(tabbedPane);
    }

    protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }

}
