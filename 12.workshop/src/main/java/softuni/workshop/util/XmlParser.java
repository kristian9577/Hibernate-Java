package softuni.workshop.util;

import javax.xml.bind.JAXBException;

public interface XmlParser {
    <O> O importXML(Class<O> objectClass, String path) throws JAXBException;

}
