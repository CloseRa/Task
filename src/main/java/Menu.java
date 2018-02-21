import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Menu extends Dish implements IMenu {

    private List<Dish> dishes;

    public List<Dish> getDishes() {
        return dishes;
    }

    @Override
    public void parseMenu(String path) {
        try {
            dishes = new ArrayList<>();

            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(path);

            Node root = document.getDocumentElement();
            NodeList dishesNode = root.getChildNodes();

            for (int i = 0; i < dishesNode.getLength(); i++) {
                Node dishNode = dishesNode.item(i);
                if (dishNode.getNodeType() != Node.TEXT_NODE) {
                    NodeList dishProps = dishNode.getChildNodes();
                    ArrayList<String> properties = new ArrayList<>();
                    for (int j = 0; j < dishProps.getLength(); j++) {
                        Node dishProp = dishProps.item(j);
                        if (dishProp.getNodeType() != Node.TEXT_NODE) {
                            properties.add(dishProp.getChildNodes().item(0).getTextContent());
                        }
                    }
                    Dish dish = new Dish(properties.get(0), Integer.parseInt(properties.get(1))
                            , Integer.parseInt(properties.get(2)));
                    dishes.add(dish);
                }
            }

        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
}