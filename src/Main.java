import com.hotels.ui.Menu;

import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Menu app = new Menu();
        app.mainMenu(sc, app);
        System.out.println("stopped");
    }
}