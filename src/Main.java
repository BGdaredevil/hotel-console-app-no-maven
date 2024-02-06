import com.hotels.auth.AuthActions;
import com.hotels.auth.User;
import com.hotels.ui.Menu;
import com.hotels.ui.MenuItem;

import java.io.Console;
import java.util.ArrayDeque;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        Menu app = new Menu();
        app = app.mainMenu(sc, app);
        System.out.println("stopped");

    }

}