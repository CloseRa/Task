import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

public class OrderHandler implements Constants {

    private IMenu menu;

    public OrderHandler(IMenu menu) {
        this.menu = menu;
    }

    public void printReports(String path){

        int ordersTotalCost = 0;

        System.out.printf("\n\n%s\n\n",CANTEENREPORT);
        List<Order> orders =  parse(path);

        for (Order order : orders){
            ordersTotalCost += order.getTotalCost();
            printReportForCanteen(order);
        }
        System.out.printf(TOTALCOST + ordersTotalCost + RUB);
        printReportForWorker(orders);
    }

    private List<Order> parse(String path) {

        List<Order> orders = new ArrayList<>();
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(path);

            Node root = document.getDocumentElement();
            NodeList ordersNode = root.getChildNodes();

            for (int i = 0; i < ordersNode.getLength(); i++) {
                Node orderNode = ordersNode.item(i);
                Order order = null;
                if (orderNode.getNodeType() != Node.TEXT_NODE) {
                    NodeList orderProps = orderNode.getChildNodes();
                    for (int j = 0; j < orderProps.getLength(); j++) {
                        Node orderProp = orderProps.item(j);
                        if (orderProp.getNodeType() != Node.TEXT_NODE) {
                            if (orderProp.getNodeName().equals(WORKER)) {
                                order = new Order(orderProp.getTextContent());
                                }else {
                                    parseDish(orderProp, order);
                            }
                        }
                    }
                    orders.add(order);
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException ex) {
            ex.printStackTrace(System.out);
        }
        return orders;
    }
    private void parseDish(Node orderProp, Order order) {
        NodeList ordersDish = orderProp.getChildNodes();
        List<Dish> dishes;
        List<String> parsedDishes = new ArrayList<>();
        for (int k = 0; k < ordersDish.getLength();k++) {
            Node orderDish = ordersDish.item(k);
            parsedDishes.add(orderDish.getTextContent());
        }
        dishes = makeDishes(parsedDishes);
        for (Dish dish: dishes) order.addDish(dish);
    }


    private List<Dish> makeDishes(List<String> strings){
        List<Dish> result = new ArrayList<>();
        List<Dish> menuDishes = menu.getDishes();
        for (String string : strings) {
            for (Dish dish : menuDishes) {
                if (dish.getName().equals(string)) {
                    result.add(dish);
                }
            }
        }
            return result;
    }

    private void printReportForWorker(List<Order> orders) {
        if(!orders.isEmpty()) {
            System.out.printf("\n\n%s\n\n", WORKERREPORT);
            for(Order order : orders) {
                System.out.println(WORKERNAME + order.getWorker());
                for (Dish dish : order.getDishes()) {
                    System.out.printf("%-40s \n", dish.getName());
                }
                System.out.printf("%s%s%s\n--------------------------------\n", TOTALCOST, order.getTotalCost(),RUB);
            }
        }else{
            System.out.println(EMPTYLIST);
        }
    }

    private void printReportForCanteen(Order order) {
        for(Dish dish : order.getDishes()){
            System.out.printf("%-40s %s %-4d%s\t %s %-3d%s\t \n", dish.getName(),
                    WEIGHT,dish.getWeight(),GRAMM,COST,dish.getCost(),RUB);
        }
    }
}