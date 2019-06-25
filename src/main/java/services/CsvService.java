package services;

import com.opencsv.CSVWriter;
import controller.EmptyListException;
import model.Event;
import view.ApplicationStarter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Dostarcza usługę zapisania stanu aplikacji do pliku .csv, które można później odtworzyć np. w aplikacji Google Calendar
 */
public class CsvService {

    /**
     * Odpowiada za zapis kontenera wydarzeń do pliku calendar.csv
     */
    public static void exportToCsv(){
        File file = new File("calendar.csv");
        try {
            FileWriter outputfile = new FileWriter(file);

            // obiekt filewriter jako parametr CSVWriter
            CSVWriter writer = new CSVWriter(outputfile);

            // nagłówki CSV
            String[] header = { "Subject", "Start Date", "Start Time", "Description", "Location" };
            writer.writeNext(header);
            String[] items = null;


            List<String> data = new ArrayList<>();
            SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:MM");
            try {
                for (Event event : ApplicationStarter.repoController.getAll()) {
                    data.add(event.getTitle());
                    data.add(dataFormat.format(event.getDate().getTime()));
                    data.add(timeFormat.format(event.getDate().getTime()));
                    data.add(event.getDescription());
                    data.add(event.getPlace());
                    items = data.toArray(new String[data.size()]);
                    writer.writeNext(items);
                    data.clear();
                }
            } catch (EmptyListException e) {
                e.printStackTrace();
            }

            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
