package application.com.car;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import application.com.car.entity.ItemRoute;

/**
 * Created by Zahit Talipov on 20.01.2016.
 */
public class AppDelegate extends Application {
    public static List<ItemRoute> itemRoutes= new ArrayList<>();
    private static String localCity = "Казань";

    public static String getLocalCity() {
        return localCity;
    }

    public static void setLocalCity(String localCity) {
        AppDelegate.localCity = localCity;
    }
}
