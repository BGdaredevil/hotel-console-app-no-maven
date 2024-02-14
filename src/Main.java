import com.hotels.auth.AuthActions;
import com.hotels.hotel.HotelService;
import com.hotels.ui.Menu;

import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Menu app = new Menu();
        app.mainMenu(sc, app);
        Main.saveAll();
        System.out.println("stopped");
    }

    private static void saveAll() {
        AuthActions.save();
        HotelService.save();
    }
}