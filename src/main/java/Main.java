
public class Main {
    public static void main(String[] args) {
        FactoryMenu factoryMenu = new FactoryMenu();
        IMenu menu = factoryMenu.createMenu("Menu");
        menu.parseMenu("menu.xml");
        new OrderHandler(menu).printReports("orders.xml");
    }
}
