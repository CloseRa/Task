import java.util.ArrayList;
import java.util.List;

public class Order {

    private String worker;
    private int totalCost;
    private List<Dish> dishes =  new ArrayList<>();

    public Order(String employeeName){
        this.worker = employeeName;
    }

    public void addDish(Dish dish){
        dishes.add(dish);
        totalCost += dish.getCost();
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public String getWorker() {
        return worker;
    }

}