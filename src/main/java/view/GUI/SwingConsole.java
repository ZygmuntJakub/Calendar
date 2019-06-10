package view.GUI;

import javax.swing.*;

/**
 * Odpowiada za uruchamianie okien aplikacji
 */
public class SwingConsole {

    /**
     * Starter okna aplikacji
     * @param f obiekt klasy JFrame, który ma zostać wyświetlony
     * @param width szerokość okna
     * @param height wysokość okna
     */
    public static void run(final JFrame f, final int width, final int height){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                f.setResizable(false);
                f.setSize(width, height);
                f.setVisible(true);
            }
        });

    }

}
