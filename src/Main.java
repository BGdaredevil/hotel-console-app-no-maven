import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hotels.auth.AuthActions;
import com.hotels.auth.User;
import com.hotels.ui.Menu;
import com.hotels.ui.MenuItem;
import com.hotels.utils.DbActions;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Menu app = new Menu();
        app.mainMenu(sc, app);
        System.out.println("stopped");
    }
}