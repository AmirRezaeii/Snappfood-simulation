package View;

import Model.*;
import Controller.*;

import java.util.Scanner;
import java.util.regex.Matcher;

public class RestaurantAdminMenu {
    private Controller controller;

    public RestaurantAdminMenu(Controller controller) {
        this.controller = controller;
    }

    public void run(Scanner scanner) {
        String command = scanner.nextLine().trim();
        while (!command.equals("logout")) {
            //System.out.println(command);
            Matcher matcherShowCurrentMenu = Commands.getMatcher(command, Commands.REGEX27);
            if (command.startsWith("charge")) {
                Matcher matcherChargeAccount = Commands.getMatcher(command, Commands.REGEX15);
                System.out.println(controller.chargeAccountForRestaurant(matcherChargeAccount));
            } else if (matcherShowCurrentMenu.find()) {
                System.out.println("restaurant admin menu");
            } else if (command.startsWith("show")) {
                Matcher matcherShowBalance = Commands.getMatcher(command, Commands.REGEX16);
                if (matcherShowBalance.find()) {
                    System.out.println(controller.showBalance(matcherShowBalance));
                } else System.out.println("invalid command!");
            } else if (command.startsWith("add")) {
                Matcher matcherAddFood = Commands.getMatcher(command, Commands.REGEX17);
                System.out.println(controller.addFood(matcherAddFood));
            } else if (command.startsWith("remove")) {
                Matcher matcherRemoveFood = Commands.getMatcher(command, Commands.REGEX18);
                String stringRemoveFood = controller.removeFood(matcherRemoveFood);
                if (!stringRemoveFood.equals("remove food successful"))
                    System.out.println(stringRemoveFood);
            } else System.out.println("invalid command!");
            command = scanner.nextLine();
            command = command.trim();
        }
    }
}
