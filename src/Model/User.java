package Model;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String type;
    private int balance= 0;

    private ArrayList<Cart> carts= new ArrayList<>();

    public User(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type= type;
    }

    public String getUsername() {
        return username;
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

    public ArrayList<Cart> getCarts() {
        return carts;
    }

    public void addCart(Cart cart){
        carts.add(cart);
    }

    public void removeCart(Cart cart){
        carts.remove(cart);
    }

    public Cart getCartByNameAndRestaurant(String name, String restaurant){
        for (Cart cart : carts){
            if(cart.getName().equals(name) && cart.getRestaurantName().equals(restaurant)) return cart;
        }
        return null;
    }

    public void purchaseCart(int amount){
        balance -= amount;
        carts.clear();
    }
}
