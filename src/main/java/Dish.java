public class Dish {

    private String name;
    private int cost;
    private int weight;

    public Dish() {
    }

    public Dish(String name, int weight, int cost){
        this.name = name;
        this.cost = cost;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getWeight() {
        return weight;
    }

}