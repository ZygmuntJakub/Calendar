package view.GUI;



import controller.DatabaseController;
import services.DatabaseService;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CalendarApplication {

    private static DatabaseService databaseService;

    public static void main(String[] args) {
        test();
        Locale.setDefault(new Locale("pl", "PL"));
        MainWindow mainWindow = new MainWindow();
        Menu menu = new Menu();
        mainWindow.setJMenuBar(menu);
        SwingConsole.run(mainWindow, 500, 600);
    }


    public static void test(){
        DatabaseController databaseController = new DatabaseController();
        try {
            databaseController.getEvent(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Calendar calendar = new GregorianCalendar();
        calendar.set(2019,5,26,23,3,12);
        System.out.println(calendar.getTime());
    }
}
