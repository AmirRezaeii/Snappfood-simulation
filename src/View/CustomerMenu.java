package View;

import java.util.Scanner;
import java.util.regex.Matcher;
import Model.*;
import Controller.*;

public class CustomerMenu {
    private Controller controller;

    public CustomerMenu(Controller controller) {
        this.controller = controller;
    }

    public void run(Scanner scanner) {
        String command = scanner.nextLine().trim();
        while (!command.equals("logout")) {
            //System.out.println(command);
            Matcher matcherShowBalance = Commands.getMatcher(command, Commands.REGEX16);
            Matcher matcherShowRestaurant = Commands.getMatcher(command, Commands.REGEX19);
            Matcher matcherShowMenuByCategory = Commands.getMatcher(command, Commands.REGEX20);
            Matcher matcherShowMenu = Commands.getMatcher(command, Commands.REGEX21);
            Matcher matcherShowCart = Commands.getMatcher(command, Commands.REGEX24);
            Matcher matcherShowDiscounts = Commands.getMatcher(command, Commands.REGEX25);
            Matcher matcherPurchaseCart = Commands.getMatcher(command, Commands.REGEX26);
            Matcher matcherShowCurrentMenu = Commands.getMatcher(command, Commands.REGEX27);
            Matcher matcherAddCartNoNumber = Commands.getMatcher(command, Commands.REGEX28);
            Matcher matcherRemoveCartNoNumber = Commands.getMatcher(command, Commands.REGEX29);
            Matcher matcherPurchaseCartNoDiscount = Commands.getMatcher(command, Commands.REGEX30);
            Matcher matcherShowRestaurantNoType = Commands.getMatcher(command, Commands.REGEX31);
            if (command.startsWith("charge")) {
                Matcher matcherChargeAccount = Commands.getMatcher(command, Commands.REGEX15);
                System.out.println(controller.chargeAccount(matcherChargeAccount));
            } else if (matcherAddCartNoNumber.find()) {
                String stringRestaurantName = matcherAddCartNoNumber.group("restaurantName");
                String stringFoodName = matcherAddCartNoNumber.group("foodName");
                System.out.println(controller.addToCartNoNumber(stringRestaurantName, stringFoodName));
            } else if (matcherRemoveCartNoNumber.find()) {
                String stringRestaurantName = matcherRemoveCartNoNumber.group("restaurantName");
                String stringFoodName = matcherRemoveCartNoNumber.group("foodName");
                System.out.println(controller.removeFromCartNoNumber(stringRestaurantName, stringFoodName));
            } else if (matcherShowBalance.find()) {
                System.out.println(controller.showBalance());
            } else if (matcherShowRestaurantNoType.find()) {
                System.out.print(controller.showRestaurantNoType());
            } else if (matcherShowRestaurant.find()) {
                String stringType = matcherShowRestaurant.group("type");
                System.out.print(controller.showRestaurant(stringType));
            } else if (matcherShowMenuByCategory.find()) {
                String stingName = matcherShowMenuByCategory.group("restaurantName");
                String stringCategory = matcherShowMenuByCategory.group("category");
                System.out.print(controller.showMenuByCategory(stingName, stringCategory));
            } else if (matcherShowMenu.find()) {
                String stringName = matcherShowMenu.group("restaurantName");
                System.out.print(controller.showMenu(stringName));
            } else if (command.startsWith("add")) {
                Matcher matcherAddToCart = Commands.getMatcher(command, Commands.REGEX22);
                System.out.println(controller.addToCart(matcherAddToCart));
            } else if (command.startsWith("remove")) {
                Matcher matcherRemoveFromCart = Commands.getMatcher(command, Commands.REGEX23);
                System.out.println(controller.removeFromCart(matcherRemoveFromCart));
            } else if (matcherShowCart.find()) {
                System.out.print(controller.showCart());
            } else if (matcherShowDiscounts.find()) {
                System.out.print(controller.showDiscount());
            } else if (matcherPurchaseCartNoDiscount.find()) {
                System.out.println(controller.purchaseCartNoDiscount());
            } else if (matcherPurchaseCart.find()) {
                String stringCode = matcherPurchaseCart.group("discountCode");
                System.out.println(controller.purchaseCart(stringCode));
            } else if (matcherShowCurrentMenu.find()) {
                System.out.println("customer menu");
            } else System.out.println("invalid command!");
            command = scanner.nextLine();
            command = command.trim();
        }
    }
}
