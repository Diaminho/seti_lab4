package sample;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SettingsXml {

    public SettingsXml() throws ParserConfigurationException, IOException, SAXException {

    }

    public static ArrayList<String> readXMLFile(String fileName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(fileName));
        Element element = document.getDocumentElement();
        //System.out.println(element.getTagName());
        //NodeList nodeList = element.getChildNodes();
        ArrayList<String> res=new ArrayList<>();
        printElement(element.getChildNodes(), res);
        return res;
    }

    public static void printElement(NodeList nodeList, ArrayList<String> res) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i) instanceof Element) {
                //System.out.println((Element) nodeList.item(i));
                if(((Element) nodeList.item(i)).hasAttribute("protocol")) {
                    System.out.println(((Element) nodeList.item(i)).getAttribute("protocol"));
                }
                else {
                    if (((Element) nodeList.item(i)).getTagName()=="port") {
                        System.out.println(((Element) nodeList.item(i)).getTagName() + ": " + nodeList.item(i).getTextContent());
                        res.add(nodeList.item(i).getTextContent());
                    }
                    else if (((Element) nodeList.item(i)).getTagName()=="server") {
                        System.out.println(((Element) nodeList.item(i)).getTagName() + ": " + nodeList.item(i).getTextContent());
                        res.add(nodeList.item(i).getTextContent());
                    }
                    else if (((Element) nodeList.item(i)).getTagName()=="log") {
                        System.out.println(((Element) nodeList.item(i)).getTagName() + ": " + nodeList.item(i).getTextContent());
                        res.add(nodeList.item(i).getTextContent());
                    }
                    //res[k / 3][k % 3] = nodeList.item(i).getTextContent();
                    //k++;
                    //str+=nodeList.item(i).getTextContent();
                }
                if (nodeList.item(i).hasChildNodes()) {
                    printElement(nodeList.item(i).getChildNodes(),res);
                }
            }
        }

    }

    public void writeXMLFile(String fileName, String[] values){

        try {
            // Строим объектную модель исходного XML файла
            final File xmlFile = new File(fileName);
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = db.parse(xmlFile);
            doc.normalize();

            // Получаем корневой элемент
            Node settings = doc.getFirstChild();

            // файла. Однако, лучше использовать более надежный метод
            // getElementsByTagName().
            Node setting=doc.getElementsByTagName("setting").item(0);

            // Для этого - пробежимся по всем дочерним элементам.
            NodeList nodeList=setting.getChildNodes();


            for (int i = 0; i < nodeList.getLength(); i++) {
                Node nextNode = nodeList.item(i);

                if (nextNode.getNodeName().equals("port")) {
                    nextNode.setTextContent(values[1]);
                } else if (nextNode.getNodeName().equals("server")) {
                    nextNode.setTextContent(values[0]);
                } else if (nextNode.getNodeName().equals("log")) {
                    nextNode.setTextContent(values[2]);
                }
            }
            // Записываем изменения в XML файл
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));
            transformer.transform(source, result);
            System.out.println("Изменения сохранены");

        } catch (SAXException | IOException | ParserConfigurationException
                | TransformerConfigurationException ex) {
            Logger.getLogger(SettingsXml.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(SettingsXml.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}


