package view;

import controller.DatabaseController;
import controller.EventController;

public class ApplicationStarter {
    public static final EventController repoController = new EventController();
    public static final DatabaseController databaseController = new DatabaseController();
}
