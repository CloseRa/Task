
public class FactoryMenu {

    public IMenu createMenu(String path) {

        if (path.equalsIgnoreCase("Menu")) {
            return new Menu();
        }else{
            throw new IllegalArgumentException();
        }
    }
}
