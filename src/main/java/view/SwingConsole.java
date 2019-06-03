package view;

import javax.swing.*;

public class SwingConsole {

    public static void run(final JFrame f, final int width, final int height){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                f.setSize(width, height);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setVisible(true);
            }
        });

    }


}
