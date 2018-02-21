import java.util.List;

public interface IMenu {
    void parseMenu(String path);
    List<Dish> getDishes();
}
