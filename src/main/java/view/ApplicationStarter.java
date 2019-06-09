package view;

import controller.DatabaseController;
import controller.EventController;
import view.GUI.GuiStarter;
import view.TUI.TuiStarter;

public class ApplicationStarter {
    public static final EventController repoController = new EventController();
    public static final DatabaseController databaseController = new DatabaseController();

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
