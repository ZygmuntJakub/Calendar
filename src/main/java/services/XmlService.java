package services;

import controller.EventController;
import controller.Events;
import view.GUI.EventEditorWindow;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlService {

    public static void marshalingExample(File file) throws JAXBException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(Events.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(EventController.list, file);
    }
    public static void unMarshalingExample(File file) throws JAXBException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(Events.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        EventEditorWindow.repoController.replaceEvents((Events) jaxbUnmarshaller.unmarshal(file));
        System.out.println(EventEditorWindow.repoController.getAllTitles());
    }
}
