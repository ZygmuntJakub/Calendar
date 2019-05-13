package view;


import com.sun.tools.javac.Main;

import java.util.Locale;

public class CalendarApplication {

    public static void main(String[] args) {
        Locale.setDefault(new Locale("pl", "PL"));
        SwingConsole.run(new MainWindow(), 800, 600);
    }
}
