import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        List<Employee> list = parseXML("data.xml");
        String json = listToJson(list);
        writeString(json);

    }

    private static void writeString(String json) {
        try (FileWriter file = new
                FileWriter("data.json")) {
            file.write(json);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static <T> String listToJson(List<Employee> list) {
        Type listType = new TypeToken<List<T>>() {}.getType();
        Gson gson = new Gson();
        String json = gson.toJson(list, listType);
        return  json;
    }

    private static List<Employee> parseXML(String s) throws ParserConfigurationException, IOException, SAXException {
        List<Employee> employees = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File("data.xml"));
        Node root = doc.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                Element element = (Element) node;
                employees.add(new Employee(element.getElementsByTagName("id").item(0).getTextContent(),
                     element.getElementsByTagName("firstName").item(0).getTextContent(),
                    element.getElementsByTagName("lastName").item(0).getTextContent(),
                    element.getElementsByTagName("country").item(0).getTextContent(),
                    element.getElementsByTagName("age").item(0).getTextContent()));
            }
        }
       return employees;
    }

}
