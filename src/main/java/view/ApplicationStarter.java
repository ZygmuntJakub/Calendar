package view;

import controller.DatabaseController;
import controller.EventController;
import services.XmlService;
import view.GUI.GuiStarter;
import view.TUI.TuiStarter;

/**
 * Klasa, gdzie podejmowana jest decyzja z jakiego UI skorzystać
 */
public class ApplicationStarter {
    /**
     * Umożliwia dostęp do operacji na wspólnym kontenerze wydarzeń dla wszystkich klas
     */
    public static final EventController repoController = new EventController();
    /**
     * Umożliwia dostęp do operacji zapisu i odczytu danych z bazy danych dla wszystkich klas
     */
    public static final DatabaseController databaseController = new DatabaseController();
    /**
     * Urochamia naszą aplikację
     * @param args jako argument należy podać nazwę UI
     */
    public static void main(String[] args) {
        if(args[0].equals("GUI")){
            GuiStarter.starter();
        }else if(args[0].equals("TUI")){
            TuiStarter.starter();
        }else{
            throw new RuntimeException("Złe argumenty do uruchomienia aplikacji!");
        }
    }
}
