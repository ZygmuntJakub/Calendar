package view.GUI;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SwingConsole {

    public static void run(final JFrame f, final int width, final int height){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                f.setSize(width, height);
                f.setVisible(true);
            }
        });

    }
}
