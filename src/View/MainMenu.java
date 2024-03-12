package View;

import Model.*;
import Controller.*;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu {
    private Controller controller;

    public MainMenu(Controller controller) {
        this.controller = controller;
    }
    public void run(Scanner scanner) {
        String command = scanner.nextLine().trim();
        while (!command.equals("logout")) {
            Matcher matcherShowCurrentMenu = Commands.getMatcher(command, Commands.REGEX27);
            //System.out.println(command);
            if (command.startsWith("enter")) {
                Matcher matcherEnter = Commands.getMatcher(command, Commands.REGEX7);
                String stringEnter = controller.enter(matcherEnter, scanner);
                if (stringEnter.equals("enter menu successful: You are in the customer menu!")) {
                    System.out.println(stringEnter);
                    CustomerMenu customerMenu = new CustomerMenu(controller);
                    customerMenu.run(scanner);
                    break;
                } else if (stringEnter.equals("enter menu successful: You are in the restaurant admin menu!")) {
                    System.out.println(stringEnter);
                    RestaurantAdminMenu restaurantAdminMenu = new RestaurantAdminMenu(controller);
                    restaurantAdminMenu.run(scanner);
                    break;
                } else if (stringEnter.equals("enter menu successful: You are in the Snappfood admin menu!")) {
                    System.out.println(stringEnter);
                    SnappfoodAdminMenu snappfoodAdminMenu = new SnappfoodAdminMenu(controller);
                    snappfoodAdminMenu.run(scanner);
                    break;
                } else System.out.println(stringEnter);
            } else if (matcherShowCurrentMenu.find()) {
                System.out.println("main menu");
            } else System.out.println("invalid command!");
            command = scanner.nextLine();
            command = command.trim();
        }
    }
}
