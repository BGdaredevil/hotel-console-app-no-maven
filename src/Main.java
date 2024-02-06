import com.hotels.ui.Menu;
import com.hotels.ui.MenuItem;

import java.util.ArrayDeque;
import java.util.Scanner;

public class Main {
    private static ArrayDeque<String> currentMenu = new ArrayDeque<>();
    private static ArrayDeque<Menu> history = new ArrayDeque<>();
    private static Menu mainMenu = new Menu(
            new MenuItem("1. View Rooms"),
            new MenuItem("2. Book a Room"),
            new MenuItem("3. Cancel booking"),
            new MenuItem("9. Admin Portal")
    );

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean initialized = true;

        System.out.println("123".hashCode());
        System.out.println("1234".hashCode());

        while (initialized) {
            System.out.println("print menu");
            System.out.print("Your choice: ");

            getMenu(currentMenu.peek());

            String command = sc.nextLine();
            currentMenu.push(command);
            System.out.println(command);

            if (command.equals("end")) {
                initialized = false;
            }
        }

    }

    private static void getMenu(String command) {
        if (command == null) {
            mainMenu.printMenu();
            history.push(mainMenu);
            return;
        }

        switch (command) {
            case "0" -> currentMenu.poll();
            default -> System.out.println("Print current menu again or the main menu if no current menu");
        }

//        Room asan = new StandardRoom(2.3, 3.3, 101);
        // print menu(command) // switch
    }
}