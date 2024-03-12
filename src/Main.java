import View.*;
import Controller.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Controller controller= new Controller();
        Scanner scanner= new Scanner(System.in);
        LoginMenu loginMenu= new LoginMenu(controller);
        loginMenu.run(scanner);
    }
}
