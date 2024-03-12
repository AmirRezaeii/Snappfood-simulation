package Model;

public class Cart {
    private String name;
    private int number;
    private String restaurantName;

    public Cart(String name, int number, String restaurantName) {
        this.name = name;
        this.number = number;
        this.restaurantName = restaurantName;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void changeNumber(int amount){
        number -= amount;
    }

    public  void addToCart(int amount){
        number += amount;
    }
}
