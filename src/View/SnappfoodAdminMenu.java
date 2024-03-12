package View;

import Model.*;
import Controller.*;

import java.util.Scanner;
import java.util.regex.Matcher;

public class SnappfoodAdminMenu {
    private Controller controller;

    public SnappfoodAdminMenu(Controller controller) {
        this.controller = controller;
    }

    public void run(Scanner scanner) {
        String command = scanner.nextLine().trim();
        while (!command.equals("logout")) {
            //System.out.println(command);
            Matcher matcherForTypeOfShow = Commands.getMatcher(command, Commands.REGEX10);
            Matcher matcherShowCurrentMenu = Commands.getMatcher(command, Commands.REGEX27);
            Matcher matcherShowRestaurantNoType = Commands.getMatcher(command, Commands.REGEX31);
            if (command.startsWith("add")) {
                Matcher matcherAddRestaurant = Commands.getMatcher(command, Commands.REGEX8);
                System.out.println(controller.addRestaurant(matcherAddRestaurant));
            } else if (matcherShowRestaurantNoType.find()) {
                System.out.print(controller.showRestaurantNoTypeInSnappfoodMenu());
            } else if (command.startsWith("show") && matcherForTypeOfShow.find()) {
                Matcher matcherShowRestaurant = Commands.getMatcher(command, Commands.REGEX10);
                System.out.print(controller.showRestaurant(matcherShowRestaurant));
            } else if (command.startsWith("remove")) {
                Matcher matcherRemoveRestaurant = Commands.getMatcher(command, Commands.REGEX11);
                String stringRemoveRestaurant = controller.removeRestaurant(matcherRemoveRestaurant);
                if (!stringRemoveRestaurant.equals("remove restaurant successful"))
                    System.out.println(stringRemoveRestaurant);
            } else if (command.startsWith("set")) {
                Matcher matcherSetDiscount = Commands.getMatcher(command, Commands.REGEX12);
                System.out.println(controller.setDiscount(matcherSetDiscount));
            } else if (matcherShowCurrentMenu.find()) {
                System.out.println("Snappfood admin menu");
            } else if (command.startsWith("show")) {
                Matcher matcherShowDiscount = Commands.getMatcher(command, Commands.REGEX14);
                System.out.print(controller.showDiscount(matcherShowDiscount));
            } else System.out.println("invalid command!");
            command = scanner.nextLine();
            command = command.trim();
        }
    }
}
