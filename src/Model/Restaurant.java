package Model;

import java.util.ArrayList;

public class Restaurant {
    private String name;
    private String password;
    private String type;
    private int balance= 0;
    private ArrayList<Food> menu= new ArrayList<>();

    public Restaurant(String name, String password, String type) {
        this.name = name;
        this.password = password;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public int getBalance() {
        return balance;
    }

    public void changeBalance(int amount){
        balance += amount;
    }

    public ArrayList<Food> getMenu() {
        return menu;
    }

    public void addFood(Food food){
        menu.add(food);
    }

    public Food getFoodByName(String name){
        for(Food food : menu){
            if(food.getName().equals(name)) return food;
        }
        return null;
    }

    public void removeFood(Food food){
        menu.remove(food);
    }
}
