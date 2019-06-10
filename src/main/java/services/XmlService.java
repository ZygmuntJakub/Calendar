package services;

import controller.EventController;
import controller.Events;
import view.ApplicationStarter;
import view.GUI.EventEditorWindow;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Klasa odpowiada za zapis/odczyt danych XML
 */
public class XmlService {

    /**
     * Zapis stanu aplikacji do XML
     * @param file plik, do którego zostaną zapisane dane
     * @throws JAXBException w razie błędu wystąpi wyjątek
     */
    public static void saveToXmlFile(File file) throws JAXBException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(Events.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(EventController.list, file);
    }

    /**
     * Odczyt stanu aplikacji do XML
     * @param file plik, z którego zostaną odczytane dane
     * @throws JAXBException w razie błędu wystąpi wyjątek
     */
    public static void importFromXmlFile(File file) throws JAXBException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(Events.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        ApplicationStarter.repoController.replaceEvents((Events) jaxbUnmarshaller.unmarshal(file));
        System.out.println(ApplicationStarter.repoController.getAllTitles());
    }
}
