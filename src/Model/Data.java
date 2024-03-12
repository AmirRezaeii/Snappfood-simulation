package Model;

import java.util.ArrayList;

public class Data {
    private static ArrayList<Restaurant> restaurants= new ArrayList<>();
    private static ArrayList<User> users= new ArrayList<>();
    private static ArrayList<Discount> discounts= new ArrayList<>();
    private static User currentUser;

    public static void setCurrentUser(User currentUser) {
        Data.currentUser = currentUser;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static User getUserByUsername(String username){
        for(User user : users)
            if(user.getUsername().equals(username)) return user;
        return null;
    }

    public static void addUser(User user){
        users.add(user);
    }

    public static ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

    public static Restaurant getRestaurantByName(String name){
        for(Restaurant restaurant : restaurants)
            if(restaurant.getName().equals(name)) return restaurant;
        return null;
    }

    public static void addRestaurant(Restaurant restaurant){
        restaurants.add(restaurant);
    }

    public static ArrayList<Discount> getDiscounts(){
        return discounts;
    }

    public static void addDiscount(Discount discount){
        discounts.add(discount);
    }

    public static Restaurant getCurrentRestaurant(){
        for (Restaurant restaurant : restaurants){
            if(currentUser.getUsername().equals(restaurant.getName())) return restaurant;
        }
        return null;
    }

    public static Discount getDiscountByCode(String code){
        for (Discount discount : discounts){
            if(discount.getCode().equals(code)) return discount;
        }
        return null;
    }
}
