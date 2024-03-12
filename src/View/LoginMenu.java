package View;

import java.util.Scanner;
import java.util.regex.Matcher;
import Model.*;
import Controller.*;

public class LoginMenu {
    private Controller controller;

    public LoginMenu(Controller controller) {
        this.controller = controller;
    }

    public void run(Scanner scanner) {
        String SnappFoodUsername = scanner.nextLine().trim();
        String SnappFoodPassword = scanner.nextLine().trim();
        User SnappFoodAdmin = new User(SnappFoodUsername, SnappFoodPassword, "Snappfood admin");
        Data.addUser(SnappFoodAdmin);
        String command = scanner.nextLine().trim();
        while (!command.equals("exit")) {
            Matcher matcherShowCurrentMenu= Commands.getMatcher(command, Commands.REGEX27);
            //System.out.println(command);
            if (command.startsWith("register")) {
                Matcher matcherRegister = Commands.getMatcher(command, Commands.REGEX1);
                System.out.println(controller.register(matcherRegister));
            } else if (command.startsWith("login")) {
                Matcher matcherLogin = Commands.getMatcher(command, Commands.REGEX4);
                String stringLogin = controller.login(matcherLogin);
                if (stringLogin.equals("login successful")) {
                    System.out.println(stringLogin);
                    MainMenu mainMenu = new MainMenu(controller);
                    mainMenu.run(scanner);
                } else System.out.println(stringLogin);
            } else if (command.startsWith("change")) {
                Matcher matcherChangePassword = Commands.getMatcher(command, Commands.REGEX5);
                System.out.println(controller.changePassword(matcherChangePassword));
            } else if (command.startsWith("remove")) {
                Matcher matcherRemoveAccount = Commands.getMatcher(command, Commands.REGEX6);
                System.out.println(controller.removeAccount(matcherRemoveAccount));
            }else if(matcherShowCurrentMenu.find()) {
                System.out.println("login menu");
            }
            else System.out.println("invalid command!");
            command = scanner.nextLine().trim();
        }
    }
}
