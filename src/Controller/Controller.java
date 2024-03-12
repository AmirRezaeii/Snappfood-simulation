package Controller;
import Model.*;

import java.util.Scanner;
import java.util.regex.Matcher;

public class Controller {
    public String chargeAccount(Matcher matcher) {
        if (matcher.find()) {
            String stringAmount = matcher.group("amount");
            if (Integer.parseInt(stringAmount) <= 0) return "charge account failed: invalid cost or price";
            Data.getCurrentUser().changeBalance(Integer.parseInt(stringAmount));
            return "charge account successful";
        } else return "invalid command!";
    }

    public int showBalance() {
        return Data.getCurrentUser().getBalance();
    }

    public String showRestaurant(String type) {
        String stringShowRestaurant = new String();
        int index = 1;
        for (Restaurant restaurant : Data.getRestaurants()) {
            if (restaurant.getType().equals(type)) {
                stringShowRestaurant = stringShowRestaurant + index + ") " + restaurant.getName() + ": type=" + restaurant.getType() + "\n";
                index++;
            }
        }
        return stringShowRestaurant;
    }

    public String showMenuByCategory(String name, String category) {
        String stringShowMenuByCategory = new String();
        if (Data.getRestaurantByName(name) == null) return "show menu failed: restaurant not found\n";
        if (!category.equals("starter") && !category.equals("entree") && !category.equals("dessert"))
            return "show menu failed: invalid category\n";
        for (Food food : Data.getRestaurantByName(name).getMenu()) {
            if (food.getCategory().equals(category))
                stringShowMenuByCategory = stringShowMenuByCategory + food.getName() + " | price=" + food.getPrice() + "\n";
        }
        return stringShowMenuByCategory;
    }

    public String showMenu(String name) {
        String stringShowMenu = new String();
        if (Data.getRestaurantByName(name) == null) return "show menu failed: restaurant not found\n";
        stringShowMenu += "<< STARTER >>\n";
        for (Food food : Data.getRestaurantByName(name).getMenu()) {
            if (food.getCategory().equals("starter"))
                stringShowMenu = stringShowMenu + food.getName() + " | price=" + food.getPrice() + "\n";
        }
        stringShowMenu += "<< ENTREE >>\n";
        for (Food food : Data.getRestaurantByName(name).getMenu()) {
            if (food.getCategory().equals("entree"))
                stringShowMenu = stringShowMenu + food.getName() + " | price=" + food.getPrice() + "\n";
        }
        stringShowMenu += "<< DESSERT >>\n";
        for (Food food : Data.getRestaurantByName(name).getMenu()) {
            if (food.getCategory().equals("dessert"))
                stringShowMenu = stringShowMenu + food.getName() + " | price=" + food.getPrice() + "\n";
        }
        return stringShowMenu;
    }

    public String addToCart(Matcher matcher) {
        if (matcher.find()) {
            String stringRestaurantName = matcher.group("restaurantName");
            String stringFoodName = matcher.group("foodName");
            String stringNumber = matcher.group("number");
            if (Data.getRestaurantByName(stringRestaurantName) == null)
                return "add to cart failed: restaurant not found";
            if (Data.getRestaurantByName(stringRestaurantName).getFoodByName(stringFoodName) == null)
                return "add to cart failed: food not found";
            if (Integer.parseInt(stringNumber) <= 0) return "add to cart failed: invalid number";
            if (Data.getCurrentUser().getCartByNameAndRestaurant(stringFoodName, stringRestaurantName) != null) {
                Data.getCurrentUser().getCartByNameAndRestaurant(stringFoodName, stringRestaurantName).addToCart(Integer.parseInt(stringNumber));
                return "add to cart successful";
            }
            Cart cart = new Cart(stringFoodName, Integer.parseInt(stringNumber), stringRestaurantName);
            Data.getCurrentUser().addCart(cart);
            return "add to cart successful";
        } else return "invalid command!";
    }

    public String removeFromCart(Matcher matcher) {
        if (matcher.find()) {
            String stringRestaurantName = matcher.group("restaurantName");
            String stringFoodName = matcher.group("foodName");
            String stringNumber = matcher.group("number");
            if (Data.getCurrentUser().getCartByNameAndRestaurant(stringFoodName, stringRestaurantName) == null)
                return "remove from cart failed: not in cart";
            if (Integer.parseInt(stringNumber) <= 0) return "remove from cart failed: invalid number";
            if (Integer.parseInt(stringNumber) > Data.getCurrentUser().getCartByNameAndRestaurant(stringFoodName, stringRestaurantName).getNumber())
                return "remove from cart failed: not enough food in cart";
            Data.getCurrentUser().getCartByNameAndRestaurant(stringFoodName, stringRestaurantName).changeNumber(Integer.parseInt(stringNumber));
            if (Data.getCurrentUser().getCartByNameAndRestaurant(stringFoodName, stringRestaurantName).getNumber() == 0)
                Data.getCurrentUser().removeCart(Data.getCurrentUser().getCartByNameAndRestaurant(stringFoodName, stringRestaurantName));
            return "remove from cart successful";
        } else return "invalid command!";
    }

    public String showCart() {
        String stringShowCart = new String();
        int index = 1;
        int total = 0;
        for (Cart cart : Data.getCurrentUser().getCarts()) {
            stringShowCart = stringShowCart + index + ") " + cart.getName() + " | restaurant=" + cart.getRestaurantName() + " price=" + cart.getNumber() * Data.getRestaurantByName(cart.getRestaurantName()).getFoodByName(cart.getName()).getPrice() + "\n";
            index++;
            total += cart.getNumber() * Data.getRestaurantByName(cart.getRestaurantName()).getFoodByName(cart.getName()).getPrice();
        }
        stringShowCart = stringShowCart + "Total: " + total + "\n";
        return stringShowCart;
    }

    public String showDiscount() {
        String stringShowDiscount = new String();
        int index = 1;
        for (Discount discount : Data.getDiscounts()) {
            if (discount.getUsername().equals(Data.getCurrentUser().getUsername())) {
                stringShowDiscount = stringShowDiscount + index + ") " + discount.getCode() + " | amount=" + discount.getAmount() + "\n";
                index++;
            }
        }
        return stringShowDiscount;
    }

    public String purchaseCart(String discountCode) {
        int amountForCustomer = 0;
        for (Discount discount : Data.getDiscounts()) {
            if (discount.getCode().equals(discountCode)) {
                for (Cart cart : Data.getCurrentUser().getCarts()) {
                    amountForCustomer += Data.getRestaurantByName(cart.getRestaurantName()).getFoodByName(cart.getName()).getPrice() * cart.getNumber();
                }
                amountForCustomer -= Data.getDiscountByCode(discountCode).getAmount();
                if (amountForCustomer > Data.getCurrentUser().getBalance()) return "purchase failed: inadequate money";
                for (Cart cart : Data.getCurrentUser().getCarts()) {
                    Restaurant restaurant = Data.getRestaurantByName(cart.getRestaurantName());
                    Food food = restaurant.getFoodByName(cart.getName());
                    restaurant.changeBalance((food.getPrice() - food.getCost()) * cart.getNumber());
                }
                Data.getCurrentUser().purchaseCart(amountForCustomer);
                Data.getDiscounts().remove(Data.getDiscountByCode(discountCode));
                return "purchase successful";
            }
        }
        return "purchase failed: invalid discount code";
    }

    public String addToCartNoNumber(String stringRestaurantName, String stringFoodName) {
        if (Data.getRestaurantByName(stringRestaurantName) == null)
            return "add to cart failed: restaurant not found";
        if (Data.getRestaurantByName(stringRestaurantName).getFoodByName(stringFoodName) == null)
            return "add to cart failed: food not found";
        if (Data.getCurrentUser().getCartByNameAndRestaurant(stringFoodName, stringRestaurantName) != null) {
            Data.getCurrentUser().getCartByNameAndRestaurant(stringFoodName, stringRestaurantName).addToCart(1);
            return "add to cart successful";
        }
        Cart cart = new Cart(stringFoodName, 1, stringRestaurantName);
        Data.getCurrentUser().addCart(cart);
        return "add to cart successful";
    }

    public String removeFromCartNoNumber(String stringRestaurantName, String stringFoodName) {
        if (Data.getCurrentUser().getCartByNameAndRestaurant(stringFoodName, stringRestaurantName) == null)
            return "remove from cart failed: not in cart";
        if (1 > Data.getCurrentUser().getCartByNameAndRestaurant(stringFoodName, stringRestaurantName).getNumber())
            return "remove from cart failed: not enough food in cart";
        Data.getCurrentUser().getCartByNameAndRestaurant(stringFoodName, stringRestaurantName).changeNumber(1);
        if (Data.getCurrentUser().getCartByNameAndRestaurant(stringFoodName, stringRestaurantName).getNumber() == 0)
            Data.getCurrentUser().removeCart(Data.getCurrentUser().getCartByNameAndRestaurant(stringFoodName, stringRestaurantName));
        return "remove from cart successful";
    }

    public String purchaseCartNoDiscount() {
        int amountForCustomer = 0;
        for (Cart cart : Data.getCurrentUser().getCarts()) {
            amountForCustomer += Data.getRestaurantByName(cart.getRestaurantName()).getFoodByName(cart.getName()).getPrice() * cart.getNumber();
        }
        if (amountForCustomer > Data.getCurrentUser().getBalance()) return "purchase failed: inadequate money";
        for (Cart cart : Data.getCurrentUser().getCarts()) {
            Restaurant restaurant = Data.getRestaurantByName(cart.getRestaurantName());
            Food food = restaurant.getFoodByName(cart.getName());
            restaurant.changeBalance((food.getPrice() - food.getCost()) * cart.getNumber());
        }
        Data.getCurrentUser().purchaseCart(amountForCustomer);
        return "purchase successful";
    }

    public String showRestaurantNoType() {
        String stringShowRestaurant = new String();
        int index = 1;
        for (Restaurant restaurant : Data.getRestaurants()) {
            stringShowRestaurant = stringShowRestaurant + index + ") " + restaurant.getName() + ": type=" + restaurant.getType() + "\n";
            index++;
        }
        return stringShowRestaurant;
    }

    public String register(Matcher matcher) {
        if (matcher.find()) {
            String stringUsername = matcher.group("username");
            String stringPassword = matcher.group("password");
            if (!Commands.getMatcher(stringUsername, Commands.REGEX2).find())
                return "register failed: invalid username format";
            if (Data.getUserByUsername(stringUsername) != null) return "register failed: username already exists";
            if (!Commands.getMatcher(stringPassword, Commands.REGEX32).find())
                return "register failed: invalid password format";
            if (!Commands.getMatcher(stringPassword, Commands.REGEX3).find()) return "register failed: weak password";
            User user = new User(stringUsername, stringPassword, "customer");
            Data.addUser(user);
            return "register successful";
        } else return "invalid command!";
    }

    public String login(Matcher matcher) {
        if (matcher.find()) {
            String stringUsername = matcher.group("username");
            String stringPassword = matcher.group("password");
            if (Data.getUserByUsername(stringUsername) == null) return "login failed: username not found";
            if (!Data.getUserByUsername(stringUsername).getPassword().equals(stringPassword))
                return "login failed: incorrect password";
            Data.setCurrentUser(Data.getUserByUsername(stringUsername));
            return "login successful";
        } else return "invalid command!";
    }

    public String changePassword(Matcher matcher) {
        if (matcher.find()) {
            String stringUsername = matcher.group("username");
            String stringOldPassword = matcher.group("oldPassword");
            String stringNewPassword = matcher.group("newPassword");
            if (Data.getUserByUsername(stringUsername) == null) return "password change failed: username not found";
            if (!Data.getUserByUsername(stringUsername).getPassword().equals(stringOldPassword))
                return "password change failed: incorrect password";
            if (!Commands.getMatcher(stringNewPassword, Commands.REGEX32).find())
                return "password change failed: invalid new password";
            if (!Commands.getMatcher(stringNewPassword, Commands.REGEX3).find())
                return "password change failed: weak new password";
            User user = new User(stringUsername, stringNewPassword, Data.getUserByUsername(stringUsername).getType());
            Data.getUsers().remove(Data.getUserByUsername(stringUsername));
            Data.addUser(user);
            return "password change successful";
        } else return "invalid command!";
    }

    public String removeAccount(Matcher matcher) {
        if (matcher.find()) {
            String stringUsername = matcher.group("username");
            String stringPassword = matcher.group("password");
            if (Data.getUserByUsername(stringUsername) == null) return "remove account failed: username not found";
            if (!Data.getUserByUsername(stringUsername).getPassword().equals(stringPassword))
                return "remove account failed: incorrect password";
            Data.getUsers().remove(Data.getUserByUsername(stringUsername));
            return "remove account successful";
        } else return "invalid command!";
    }

    public String enter(Matcher matcher, Scanner scanner) {
        if (matcher.find()) {
            String stringMenuName = matcher.group("menuName");
            if (stringMenuName.equals("customer menu")) {
                if (Data.getCurrentUser().getType().equals("customer")) {
                    return "enter menu successful: You are in the customer menu!";
                } else return "enter menu failed: access denied";
            } else if (stringMenuName.equals("restaurant admin menu")) {
                if (Data.getCurrentUser().getType().equals("restaurant admin")) {
                    return "enter menu successful: You are in the restaurant admin menu!";
                } else return "enter menu failed: access denied";
            } else if (stringMenuName.equals("Snappfood admin menu")) {
                if (Data.getCurrentUser().getType().equals("Snappfood admin")) {
                    return "enter menu successful: You are in the Snappfood admin menu!";
                } else return "enter menu failed: access denied";
            } else return "enter menu failed: invalid menu name";
        } else return "invalid command!";
    }

    public String chargeAccountForRestaurant(Matcher matcher) {
        if (matcher.find()) {
            String stringAmount = matcher.group("amount");
            if (Integer.parseInt(stringAmount) <= 0) return "charge account failed: invalid cost or price";
            Data.getCurrentRestaurant().changeBalance(Integer.parseInt(stringAmount));
            return "charge account successful";
        } else return "invalid command!";
    }

    public int showBalance(Matcher matcher) {
        return Data.getCurrentRestaurant().getBalance();
    }

    public String addFood(Matcher matcher) {
        if (matcher.find()) {
            String stringName = matcher.group("name");
            String stringCategory = matcher.group("category");
            String stringPrice = matcher.group("price");
            String stringCost = matcher.group("cost");
            if (!stringCategory.equals("starter") && !stringCategory.equals("entree") && !stringCategory.equals("dessert"))
                return "add food failed: invalid category";
            if (!Commands.getMatcher(stringName, Commands.REGEX9).find()) return "add food failed: invalid food name";
            if (Data.getCurrentRestaurant().getFoodByName(stringName) != null)
                return "add food failed: food already exists";
            if (Integer.parseInt(stringPrice) <= 0 || Integer.parseInt(stringCost) <= 0)
                return "add food failed: invalid cost or price";
            Food food = new Food(stringName, stringCategory, Integer.parseInt(stringPrice), Integer.parseInt(stringCost));
            Data.getCurrentRestaurant().addFood(food);
            return "add food successful";
        } else return "invalid command!";
    }

    public String removeFood(Matcher matcher) {
        if (matcher.find()) {
            String stringName = matcher.group("name");
            if (Data.getCurrentRestaurant().getFoodByName(stringName) == null)
                return "remove food failed: food not found";
            Data.getCurrentRestaurant().getMenu().remove(Data.getCurrentRestaurant().getFoodByName(stringName));
            return "remove food successful";
        } else return "invalid command!";
    }

    public String addRestaurant(Matcher matcher) {
        if (matcher.find()) {
            String stringUsername = matcher.group("username");
            String stringPassword = matcher.group("password");
            String stringTypeRestaurant = matcher.group("typeRestaurant");
            if (!Commands.getMatcher(stringUsername, Commands.REGEX2).find())
                return "add restaurant failed: invalid username format";
            if (Data.getUserByUsername(stringUsername) != null) return "add restaurant failed: username already exists";
            if (!Commands.getMatcher(stringPassword, Commands.REGEX32).find())
                return "add restaurant failed: invalid password format";
            if (!Commands.getMatcher(stringPassword, Commands.REGEX3).find())
                return "add restaurant failed: weak password";
            if (!Commands.getMatcher(stringTypeRestaurant, Commands.REGEX9).find())
                return "add restaurant failed: invalid type format";
            User user = new User(stringUsername, stringPassword, "restaurant admin");
            Data.addUser(user);
            Restaurant restaurant = new Restaurant(stringUsername, stringPassword, stringTypeRestaurant);
            Data.addRestaurant(restaurant);
            return "add restaurant successful";
        } else return "invalid command!";
    }

    public String showRestaurant(Matcher matcher) {
        if (matcher.find()) {
            String stringType = matcher.group("type");
            String stringShowRestaurant = new String();
            int index = 1;
            for (Restaurant restaurant : Data.getRestaurants()) {
                if (restaurant.getType().equals(stringType)) {
                    stringShowRestaurant = stringShowRestaurant + index + ") " + restaurant.getName() + ": type=" + restaurant.getType() + " balance=" + restaurant.getBalance() + "\n";
                    index++;
                }
            }
            return stringShowRestaurant;
        } else return "invalid command!\n";
    }

    public String removeRestaurant(Matcher matcher) {
        if (matcher.find()) {
            String stringName = matcher.group("name");
            for (Restaurant restaurant : Data.getRestaurants()) {
                if (restaurant.getName().equals(stringName)) {
                    Data.getRestaurants().remove(restaurant);
                    Data.getUsers().remove(Data.getUserByUsername(stringName));
                    return "remove restaurant successful";
                }
            }
            return "remove restaurant failed: restaurant not found";
        } else return "invalid command!";
    }

    public String setDiscount(Matcher matcher) {
        if (matcher.find()) {
            String stringUsername = matcher.group("username");
            String stringAmount = matcher.group("amount");
            String stringCode = matcher.group("code");
            if (Data.getUserByUsername(stringUsername) == null ||
                    Data.getUserByUsername(stringUsername).getType().equals("restaurant admin") ||
                    Data.getUserByUsername(stringUsername).getType().equals("Snappfood admin"))
                return "set discount failed: username not found";
            if (Integer.parseInt(stringAmount) <= 0) return "set discount failed: invalid amount";
            if (!Commands.getMatcher(stringCode, Commands.REGEX13).find())
                return "set discount failed: invalid code format";
            Discount discount = new Discount(stringUsername, Integer.parseInt(stringAmount), stringCode);
            Data.addDiscount(discount);
            return "set discount successful";
        } else return "invalid command!";
    }

    public String showDiscount(Matcher matcher) {
        if (matcher.find()) {
            String stringShowDiscount = new String();
            int index = 1;
            for (Discount discount : Data.getDiscounts()) {
                stringShowDiscount = stringShowDiscount + index + ") " + discount.getCode() + " | amount=" + discount.getAmount() + " --> user=" + discount.getUsername() + "\n";
                index++;
            }
            return stringShowDiscount;
        } else return "invalid command!\n";
    }

    public String showRestaurantNoTypeInSnappfoodMenu() {
        String stringShowRestaurant = new String();
        int index = 1;
        for (Restaurant restaurant : Data.getRestaurants()) {
            stringShowRestaurant = stringShowRestaurant + index + ") " + restaurant.getName() + ": type=" + restaurant.getType() + " balance=" + restaurant.getBalance() + "\n";
            index++;
        }
        return stringShowRestaurant;
    }
}
